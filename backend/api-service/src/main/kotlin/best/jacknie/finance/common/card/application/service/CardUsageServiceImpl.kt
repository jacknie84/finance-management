package best.jacknie.finance.common.card.application.service

import best.jacknie.finance.common.card.application.port.*
import best.jacknie.finance.common.card.domain.CardUsageEntity
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.adapter.export.ExportedSpendingLogService
import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardUsageServiceImpl(
  private val cardOutPort: CardOutPort,
  private val usageOutPort: CardUsageOutPort,
  private val logService: ExportedSpendingLogService,
): CardUsageService {

  override fun createCardUsage(cardId: Long, dto: SaveCardUsage): CardUsageEntity {
    val card = cardOutPort.findById(cardId) ?: throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(cardId: $cardId)")
    try {
      val entity = usageOutPort.create(dto, card)
      val log = SaveSpendingLog(
        summary = dto.merchant,
        amount = dto.amount,
        time =  dto.time,
        username = card.user.name,
      )
      logService.createSpendingLog(log, entity.id!!)
      return entity
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardUsagesPage(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    if (!cardOutPort.existsById(cardId)) {
      throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(cardId: $cardId)")
    } else {
      return usageOutPort.findAll(cardId, filter, pageable)
    }
  }

  override fun putCardUsage(cardId: Long, id: Long, dto: SaveCardUsage) {
    val entity = usageOutPort.findOne(cardId, id) ?: throw HttpStatusCodeException.NotFound("카드 사용 내역 정보를 찾을 수 없습니다(cardId: $cardId, id: $id)")
    try {
      val log = SaveSpendingLog(
        summary = dto.merchant,
        amount = dto.amount,
        time =  dto.time,
        username = entity.card.user.name,
      )
      usageOutPort.update(entity, dto)
      logService.updateSpendingLog(log, entity.id!!)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.approvalNumber)
    }
  }

  private fun handleDataIntegrityViolationException(e: DataIntegrityViolationException, approvalNumber: String?): HttpStatusCodeException {
    return if (approvalNumber != null && usageOutPort.existsByApprovalNumber(approvalNumber)) {
      HttpStatusCodeException.Conflict("already_exists", "이미 등록 된 카드 사용 승인 번호 입니다(${approvalNumber})")
    } else {
      HttpStatusCodeException.InternalServerError(e.message)
    }
  }
}
