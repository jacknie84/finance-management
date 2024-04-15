package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardUsageServiceImpl(
  private val usageOutPort: CardUsageOutPort,
  private val logOutPort: SpendingLogOutPort,
  private val logTagOutPort: SpendingLogTagOutPort,
  private val cardOutPort: CardOutPort,
  private val fileOutPort: CardUsageFileOutPort,
): CardUsageService {

  @Transactional
  override fun createCardUsage(cardId: Long, dto: SaveCardUsage): CardUsage {
    val card = cardOutPort.getCard(cardId)
    try {
      val log = logOutPort.create(dto, card.user)
      val file = getCardUsageFile(cardId, dto.fileId)
      val usage = usageOutPort.create(dto, file, log)
      val tags = if (dto.tags.isNullOrEmpty()) {
        logTagOutPort.findAllByLogId(log.id!!)
      } else {
        logTagOutPort.replaceAll(log, dto.tags)
      }
      return CardUsage.from(usage, tags)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsage> {
    cardOutPort.getCard(cardId)
    val page = usageOutPort.findAll(cardId, filter, pageable)
    val logIds = page.map { it.log.id!! }.toSet()
    val tagsMap = logTagOutPort.getMapByLogId(logIds)
    return page.map { CardUsage.from(it, tagsMap[it.log.id!!]) }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsage> {
    val page = usageOutPort.findAll(filter, pageable)
    val logIds = page.map { it.log.id!! }.toSet()
    val tagsMap = logTagOutPort.getMapByLogId(logIds)
    return page.map { CardUsage.from(it, tagsMap[it.log.id!!]) }
  }

  @Transactional
  override fun putCardUsage(cardId: Long, id: Long, dto: SaveCardUsage): CardUsage {
    var usage = usageOutPort.findOne(cardId, id) ?: notFound(cardId, id)
    try {
      val file = getCardUsageFile(cardId, dto.fileId)
      val log = logOutPort.update(usage.log, dto, file.card.user)
      usage = usageOutPort.update(usage, dto, file)
      val tags = if (dto.tags.isNullOrEmpty()) {
        logTagOutPort.findAllByLogId(log.id!!)
      } else {
        logTagOutPort.replaceAll(log, dto.tags)
      }
      return CardUsage.from(usage, tags)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  private fun getCardUsageFile(cardId: Long, fileId: Long): CardUsageFileEntity {
    return fileOutPort.findOne(cardId, fileId)
      ?: throw HttpStatusCodeException.BadRequest("카드 사용 내역 파일을 찾을 수 없습니다(cardId: $cardId, fileId: ${fileId})")
  }

  private fun notFound(cardId: Long, id: Long): Nothing = throw HttpStatusCodeException.NotFound("카드 사용 내역 정보를 찾을 수 없습니다(cardId: $cardId, id: $id)")

  private fun handleDataIntegrityViolationException(e: DataIntegrityViolationException, approvalNumber: String?): HttpStatusCodeException {
    return if (approvalNumber != null && usageOutPort.existsByApprovalNumber(approvalNumber)) {
      HttpStatusCodeException.Conflict("already_exists", "이미 등록 된 카드 사용 승인 번호 입니다(${approvalNumber})")
    } else {
      HttpStatusCodeException.InternalServerError(e.message)
    }
  }
}
