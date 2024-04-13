package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
import best.jacknie.finance.spending.log.domain.QCardUsageEntity.cardUsageEntity
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.allOf
import jakarta.validation.constraints.Positive

data class CardUsagesFilter(

  /**
   * 카드 아이디 목록
   */
  var cardId: Set<@Positive Long>? = null,

  /**
   * 승인 번호 목록
   */
  var approvalNumber: Set<String>? = null,

  /**
   * 검색어 목록
   */
  var search001: Set<String>? = null,

): PredicateProvider {

  override val predicate: Predicate? get() {
    return allOf(
      cardId?.let { cardUsageEntity.file.card.id.`in`(it) },
      approvalNumber?.let { cardUsageEntity.approvalNumber.`in`(it) },
      search001
        ?.map {
          cardUsageEntity.approvalNumber.containsIgnoreCase(it)
            .or(cardUsageEntity.merchant.containsIgnoreCase(it))
            .or(cardUsageEntity.file.description.containsIgnoreCase(it))
            .or(cardUsageEntity.file.card.name.containsIgnoreCase(it))
        }
        ?.reduce(BooleanExpression::or)
    )
  }
}
