package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.QSpendingLogEntity.spendingLogEntity
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import com.querydsl.core.types.ExpressionUtils.*
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.SimpleExpression
import com.querydsl.jpa.JPAExpressions.selectOne
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository("spendingLogRepositoryImpl")
class SpendingLogCustomRepositoryImpl: PagingRepositorySupport(SpendingLogEntity::class),
  SpendingLogCustomRepository {

  override fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity> {
    return getPage(spendingLogEntity, pageable) { getPredicate(filter) }
  }

  override fun findTotalAmount(filter: SpendingStatisticsFilter): Int {
    return from(spendingLogEntity).select(spendingLogEntity.amount.sum())
      .where(getPredicate(filter))
      .fetchOne()
  }

  override fun findYearStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.time.year, filter)
  }

  override fun findMonthStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.time.month, filter)
  }

  override fun findDayStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.time.dayOfMonth, filter)
  }

  override fun findDowStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.time.dayOfWeek, filter)
  }

  override fun findHourStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.time.hour, filter)
  }

  override fun findUserStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    return findSpendingStatistics(spendingLogEntity.user.name, filter)
  }

  private fun <T> findSpendingStatistics(expression: SimpleExpression<T>, filter: SpendingStatisticsFilter): List<SpendingStatistics.Item> {
    val item = QSpendingStatistics_Item(
      expression,
      spendingLogEntity.amount.sum(),
      expression.count()
    )
    return from(spendingLogEntity).select(item)
      .where(getPredicate(filter))
      .groupBy(expression)
      .fetch()
  }

  private fun getPredicate(filter: SpendingLogsFilter): Predicate? {
    return allOf(
      filter.search001?.takeIf { it.isNotEmpty() }?.let { getSearch001Predicate(it) },
      filter.start?.let { spendingLogEntity.time.instant.goe(it) },
      filter.end?.let { spendingLogEntity.time.instant.lt(it) },
      filter.conditions
        ?.map { getPredicate(it, this::getQueryConditionBooleanExpression) }
        ?.reduce { acc, it -> or(acc, it) }
    )
  }

  private fun getPredicate(filter: SpendingStatisticsFilter): Predicate? {
    return allOf(
      filter.search001?.takeIf { it.isNotEmpty() }?.let { getSearch001Predicate(it) },
      filter.start?.let { spendingLogEntity.time.instant.goe(it) },
      filter.end?.let { spendingLogEntity.time.instant.lt(it) },
    )
  }

  private fun getSearch001Predicate(search001: Set<String>): Predicate? {
    return anyOf(
      search001
        .map { spendingLogEntity.summary.containsIgnoreCase(it) }
        .reduce(BooleanExpression::or),
      search001
        .let {
          selectOne().from(spendingLogTagEntity)
            .where(
              spendingLogTagEntity.log.eq(spendingLogEntity),
              it
                .map { search -> spendingLogTagEntity.tag.containsIgnoreCase(search) }
                .reduce(BooleanExpression::or),
            )
            .exists()
        }
    )
  }

  private fun getQueryConditionBooleanExpression(condition: SpendingLogsFilter.QueryCondition): BooleanExpression? {
    return when (condition) {
      SpendingLogsFilter.QueryCondition.EMPTY_TAGS -> selectOne().from(spendingLogTagEntity)
        .where(spendingLogTagEntity.log.eq(spendingLogEntity))
        .notExists()
    }
  }
}
