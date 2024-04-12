package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.CardUsageEntity
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
  private val cardOutPort: CardOutPort,
  private val fileOutPort: CardUsageFileOutPort,
): CardUsageService {

  @Transactional
  override fun createCardUsage(cardId: Long, dto: SaveCardUsage): CardUsageEntity {
    val card = cardOutPort.getCard(cardId)
    try {
      val log = logOutPort.create(dto, card.user)
      val file = getCardUsageFile(cardId, dto.fileId)
      return usageOutPort.create(dto, file, log)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    cardOutPort.getCard(cardId)
    return usageOutPort.findAll(cardId, filter, pageable)
  }

  @Transactional
  override fun putCardUsage(cardId: Long, id: Long, dto: SaveCardUsage) {
    val usage = usageOutPort.findOne(cardId, id) ?: notFound(cardId, id)
    try {
      val file = getCardUsageFile(cardId, dto.fileId)
      logOutPort.update(usage.log, dto, file.card.user)
      usageOutPort.update(usage, dto, file)
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
