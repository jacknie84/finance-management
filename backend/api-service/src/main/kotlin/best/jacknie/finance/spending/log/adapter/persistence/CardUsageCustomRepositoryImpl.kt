package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.CardUsageCustomRepository
import best.jacknie.finance.spending.log.application.port.CardUsagesFilter
import best.jacknie.finance.spending.log.domain.CardUsageEntity
import best.jacknie.finance.spending.log.domain.QCardUsageEntity.cardUsageEntity
import com.querydsl.core.types.ExpressionUtils.allOf
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository("cardUsageRepositoryImpl")
class CardUsageCustomRepositoryImpl: PagingRepositorySupport(CardUsageEntity::class), CardUsageCustomRepository {

  override fun findByCardIdAndId(cardId: Long, id: Long): CardUsageEntity? {
    return from(cardUsageEntity)
      .where(
        cardUsageEntity.file.card.id.eq(cardId),
        cardUsageEntity.id.eq(id)
      )
      .fetchOne()
  }

  override fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return getPage(joinQuery(), pageable) { allOf(cardUsageEntity.file.card.id.eq(cardId), getPredicate(filter)) }
  }

  override fun findAll(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity> {
    return getPage(joinQuery(), pageable) { getPredicate(filter) }
  }

  private fun joinQuery() = from(cardUsageEntity)
    .join(cardUsageEntity.log).fetchJoin()
    .join(cardUsageEntity.file).fetchJoin()

  private fun getPredicate(filter: CardUsagesFilter): Predicate? {
    return allOf(
      filter.cardId?.let { cardUsageEntity.file.card.id.`in`(it) },
      filter.approvalNumber?.let { cardUsageEntity.approvalNumber.`in`(it) },
      filter.search001
        ?.map {
          cardUsageEntity.approvalNumber.containsIgnoreCase(it)
            .or(cardUsageEntity.merchant.containsIgnoreCase(it))
            .or(cardUsageEntity.file.description.containsIgnoreCase(it))
            .or(cardUsageEntity.file.card.name.containsIgnoreCase(it))
        }
        ?.reduce(BooleanExpression::or),
      filter.start?.let { cardUsageEntity.log.time.instant.goe(it) },
      filter.end?.let { cardUsageEntity.log.time.instant.lt(it) },
    )
  }
}
