package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.application.port.CardUsageOutPort
import best.jacknie.finance.common.card.application.port.CardUsagesFilter
import best.jacknie.finance.common.card.application.port.SaveCardUsage
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.card.domain.CardUsageEntity
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CardUsagePersistenceAdapter(
  private val usageRepository: CardUsageRepository,
  private val entityManager: EntityManager,
): CardUsageOutPort {

  @Transactional
  override fun create(dto: SaveCardUsage, card: CardEntity): CardUsageEntity {
    val entity = CardUsageEntity(
      approvalNumber = dto.approvalNumber,
      merchant = dto.merchant,
      status = dto.status,
      card = card,
    )
    return save(entity)
  }

  @Transactional(readOnly = true)
  override fun existsByApprovalNumber(approvalNumber: String): Boolean {
    return usageRepository.existsByApprovalNumber(approvalNumber)
  }

  @Transactional(readOnly = true)
  override fun findOne(cardId: Long, id: Long): CardUsageEntity? {
    return usageRepository.findByCardIdAndId(cardId, id)
  }

  @Transactional
  override fun update(entity: CardUsageEntity, dto: SaveCardUsage): CardUsageEntity {
    entity.apply {
      approvalNumber = dto.approvalNumber
      merchant = dto.merchant
      status = dto.status
    }
    return usageRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return usageRepository.findAll(cardId, filter, pageable)
  }

  private fun save(entity: CardUsageEntity): CardUsageEntity {
    try {
      return usageRepository.save(entity)
    } catch (e: Exception) {
      entityManager.clear()
      throw e
    }
  }
}
