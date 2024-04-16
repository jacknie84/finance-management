package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
import best.jacknie.finance.spending.log.domain.QSpendingLogEntity.spendingLogEntity
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import com.querydsl.core.types.ExpressionUtils.allOf
import com.querydsl.core.types.ExpressionUtils.anyOf
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions.selectOne
import java.time.Instant

data class SpendingLogsFilter(

  /**
   * 검색 목록
   */
  var search001: Set<String>?,

  /**
   * 검색 시작 시간
   */
  var start: Instant?,

  /**
   * 검색 종료 시간
   */
  var end: Instant?,

): PredicateProvider {

  override val predicate: Predicate? get() {
    return allOf(
      search001Predicate(),
      start?.let { spendingLogEntity.time.instant.goe(it) },
      end?.let { spendingLogEntity.time.instant.lt(it) }
    )
  }

  private fun search001Predicate(): Predicate? {
    return anyOf(
      search001
        ?.map { spendingLogEntity.summary.containsIgnoreCase(it) }
        ?.reduce(BooleanExpression::or),
      search001
        ?.let {
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
}
