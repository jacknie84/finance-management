package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.QSpendingStatistics_Item
import best.jacknie.finance.spending.log.application.port.SpendingLogTagCustomRepository
import best.jacknie.finance.spending.log.application.port.SpendingStatistics
import best.jacknie.finance.spending.log.application.port.SpendingStatisticsFilter
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.stereotype.Repository

@Repository("spendingLogTagRepositoryImpl")
class SpendingLogTagCustomRepositoryImpl: PagingRepositorySupport(SpendingLogTagEntity::class),
  SpendingLogTagCustomRepository {

  override fun findAllPreset(): List<String> {
    return from(spendingLogTagEntity).select(spendingLogTagEntity.tag).groupBy(spendingLogTagEntity.tag).fetch()
  }

  override fun findAllRecommended(summary: String): List<SpendingLogTagEntity> {
    return from(spendingLogTagEntity)
      .join(spendingLogTagEntity.log).fetchJoin()
      .where(spendingLogTagEntity.log.summary.containsIgnoreCase(summary))
      .fetch()
  }

  override fun findSpendingStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    val item = QSpendingStatistics_Item(
      spendingLogTagEntity.tag,
      spendingLogTagEntity.log.amount.sum(),
      spendingLogTagEntity.tag.count()
    )
    return from(spendingLogTagEntity).select(item)
      .join(spendingLogTagEntity.log)
      .where(
        filter.search001?.takeIf { it.isNotEmpty() }?.let { getSearch001Predicate(it) },
        filter.start?.let { spendingLogTagEntity.log.time.instant.goe(it) },
        filter.end?.let { spendingLogTagEntity.log.time.instant.lt(it) },
      )
      .groupBy(spendingLogTagEntity.tag)
      .fetch()
  }

  private fun getSearch001Predicate(search001: Set<String>): Predicate? {
    return search001
      .map { spendingLogTagEntity.tag.containsIgnoreCase(it)
        .or(spendingLogTagEntity.log.summary.containsIgnoreCase(it)) }
      .reduce(BooleanExpression::or)
  }
}
