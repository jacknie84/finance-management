package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.spending.log.adapter.persistence.jpa.SpendingLogCardUsageRepository
import best.jacknie.finance.spending.log.application.port.SpendingLogCardUsageOutPort
import best.jacknie.finance.spending.log.domain.SpendingLogCardUsageEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SpendingLogCardUsagePersistenceAdapter(
  private val logCardUsageRepository: SpendingLogCardUsageRepository
): SpendingLogCardUsageOutPort {

  @Transactional
  override fun create(log: SpendingLogEntity, cardUsageId: Long): SpendingLogCardUsageEntity {
    val entity = SpendingLogCardUsageEntity(
      logId = log.id!!,
      cardUsageId = cardUsageId,
      log = log
    )
    return logCardUsageRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findByCardId(cardId: Long): SpendingLogCardUsageEntity? {
    return logCardUsageRepository.findByCardUsageId(cardId)
  }
}
