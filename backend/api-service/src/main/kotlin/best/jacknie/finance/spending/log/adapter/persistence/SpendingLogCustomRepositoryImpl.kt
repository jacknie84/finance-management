package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.SpendingLogCustomRepository
import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import best.jacknie.finance.spending.log.domain.QSpendingLogEntity.spendingLogEntity
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import com.querydsl.core.types.ExpressionUtils.*
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
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

  private fun getPredicate(filter: SpendingLogsFilter): Predicate? {
    return allOf(
      filter.search001?.let { getSearch001Predicate(it) },
      filter.start?.let { spendingLogEntity.time.instant.goe(it) },
      filter.end?.let { spendingLogEntity.time.instant.lt(it) },
      filter.conditions
        ?.map { getPredicate(it, this::getQueryConditionBooleanExpression) }
        ?.reduce { acc, it -> or(acc, it) }
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
