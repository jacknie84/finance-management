package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.application.port.CardUsagesFilter
import best.jacknie.finance.spending.log.domain.CardUsageEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.domain.QCardUsageEntity.cardUsageEntity
import com.querydsl.core.types.ExpressionUtils.allOf
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Suppress("unused")
class CardUsageCustomRepositoryImpl: PagingRepositorySupport(CardUsageEntity::class), CardUsageCustomRepository {

  override fun existsByApprovalNumber(approvalNumber: String): Boolean {
    val id = from(cardUsageEntity).select(cardUsageEntity.id)
      .where(cardUsageEntity.approvalNumber.eq(approvalNumber))
      .fetchFirst()
    return id != null
  }

  override fun findByCardIdAndId(cardId: Long, id: Long): CardUsageEntity? {
    return from(cardUsageEntity)
      .where(
        cardUsageEntity.file.card.id.eq(cardId),
        cardUsageEntity.id.eq(id)
      )
      .fetchOne()
  }

  override fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return getPage(cardUsageEntity, pageable) { allOf(cardUsageEntity.file.card.id.eq(cardId), filter.predicate) }
  }

  override fun findAll(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return getPage(cardUsageEntity, pageable) { filter.predicate }
  }
}
