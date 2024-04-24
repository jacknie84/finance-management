package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.core.jpa.utils.isDuplicateKeyError
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.CardUsageEntity
import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingTime
import jakarta.persistence.EntityManager
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardUsageServiceImpl(
  private val usageRepository: CardUsageRepository,
  private val logRepository: SpendingLogRepository,
  private val tagRepository: SpendingLogTagRepository,
  private val cardClient: CardClient,
  private val fileRepository: CardUsageFileRepository,
  private val entityManager: EntityManager,
): CardUsageService {

  @Transactional
  override fun createCardUsage(cardId: Long, dto: SaveCardUsage): CardUsage {
    try {
      return createCardUsageInternal(cardId, dto)
    } catch (e: DataIntegrityViolationException) {
      entityManager.clear()
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsage> {
    cardClient.getCard(cardId)
    val page = usageRepository.findAll(cardId, filter, pageable)
    val logIds = page.map { it.log.id!! }.toSet()
    val tagsMap = tagRepository.findMapByLogId(logIds)
    return page.map { CardUsage.from(it, tagsMap[it.log.id!!]) }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsage> {
    val page = usageRepository.findAll(filter, pageable)
    val logIds = page.map { it.log.id!! }.toSet()
    val tagsMap = tagRepository.findMapByLogId(logIds)
    return page.map { CardUsage.from(it, tagsMap[it.log.id!!]) }
  }

  @Transactional
  override fun putCardUsage(cardId: Long, id: Long, dto: SaveCardUsage): CardUsage {
    try {
      return putCardUsageInternal(cardId, id, dto)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  private fun createCardUsageInternal(cardId: Long, dto: SaveCardUsage): CardUsage {
    val card = cardClient.getCard(cardId)
    val log = logRepository.save(SpendingLogEntity(
      summary = dto.merchant,
      amount = if (dto.status.positive) dto.amount else -(dto.amount),
      time = SpendingTime.from(dto.time),
      user = card.user,
    ))
    val file = getCardUsageFile(cardId, dto.fileId)
    val usage = usageRepository.save(CardUsageEntity(
      approvalNumber = dto.approvalNumber,
      merchant = dto.merchant,
      status = dto.status,
      file = file,
      log = log,
    ))
    val tags = tagRepository.findOrReplaceAll(log, dto.tags)
    return CardUsage.from(usage, tags)
  }

  private fun putCardUsageInternal(cardId: Long, id: Long, dto: SaveCardUsage): CardUsage {
    var usage = usageRepository.findByCardIdAndId(cardId, id) ?: notFound(cardId, id)
    val file = getCardUsageFile(cardId, dto.fileId)
    usage.log.apply {
      summary = dto.merchant
      amount = if (dto.status.positive) dto.amount else -(dto.amount)
      time = SpendingTime.from(dto.time)
      user = file.card.user
    }
    val log = logRepository.save(usage.log)
    usage.apply {
      approvalNumber = dto.approvalNumber
      merchant = dto.merchant
      status = dto.status
      this.file = file
    }
    usage = usageRepository.save(usage)
    val tags = tagRepository.findOrReplaceAll(log, dto.tags)
    return CardUsage.from(usage, tags)
  }

  private fun getCardUsageFile(cardId: Long, fileId: Long): CardUsageFileEntity {
    return fileRepository.findByCardIdAndId(cardId, fileId)
      ?: throw HttpStatusCodeException.BadRequest("카드 사용 내역 파일을 찾을 수 없습니다(cardId: $cardId, fileId: ${fileId})")
  }

  private fun notFound(cardId: Long, id: Long): Nothing = throw HttpStatusCodeException.NotFound("카드 사용 내역 정보를 찾을 수 없습니다(cardId: $cardId, id: $id)")

  private fun handleDataIntegrityViolationException(e: DataIntegrityViolationException, approvalNumber: String): HttpStatusCodeException {
    return if (isDuplicateKeyError(e)) {
      HttpStatusCodeException.Conflict("already_exists", "이미 등록 된 카드 사용 승인 번호 입니다(${approvalNumber})")
    } else {
      HttpStatusCodeException.InternalServerError(e.message)
    }
  }
}
