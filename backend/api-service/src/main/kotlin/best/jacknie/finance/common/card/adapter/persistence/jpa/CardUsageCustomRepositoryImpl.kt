package best.jacknie.finance.common.card.adapter.persistence.jpa

import best.jacknie.finance.common.card.application.port.CardUsagesFilter
import best.jacknie.finance.common.card.domain.CardUsageEntity
import best.jacknie.finance.common.card.domain.QCardUsageEntity.cardUsageEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
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

  override fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return getPage(cardUsageEntity, pageable) { allOf(cardUsageEntity.card.id.eq(cardId), filter.predicate) }
  }
}
