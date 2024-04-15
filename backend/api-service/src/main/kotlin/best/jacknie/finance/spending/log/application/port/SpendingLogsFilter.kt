package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
import best.jacknie.finance.spending.log.domain.QSpendingLogEntity.spendingLogEntity
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import com.querydsl.core.types.ExpressionUtils.allOf
import com.querydsl.core.types.ExpressionUtils.anyOf
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions.selectOne
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import java.time.DayOfWeek
import java.time.Month

data class SpendingLogsFilter(

  /**
   * 검색 목록
   */
  var search001: Set<String>?,

  /**
   * 연도 목록
   */
  var year: Set<@Positive @Min(value = 1970) Int>?,

  /**
   * 월 목록
   */
  var month: Set<Month>?,

  /**
   * 일 목록
   */
  var dayOfMonth: Set<@Min(value = 1) @Max(value = 31) Int>?,

  /**
   * 요일 목록
   */
  var dayOfWeek: Set<DayOfWeek>?,

): PredicateProvider {

  override val predicate: Predicate? get() {
    return allOf(
      anyOf(
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
      ),
      year?.let { spendingLogEntity.time.year.`in`(it) },
      month?.let { spendingLogEntity.time.month.`in`(it) },
      dayOfMonth?.let { spendingLogEntity.time.dayOfMonth.`in`(it) },
      dayOfWeek?.let { spendingLogEntity.time.dayOfWeek.`in`(it) },
    )
  }
}
