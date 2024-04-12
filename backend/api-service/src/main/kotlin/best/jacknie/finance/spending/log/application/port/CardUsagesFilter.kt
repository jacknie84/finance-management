package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
import best.jacknie.finance.spending.log.domain.QCardUsageEntity.cardUsageEntity
import com.querydsl.core.types.Predicate

data class CardUsagesFilter(

  /**
   * 승인 번호 목록
   */
  var approvalNumber: Set<String>? = null,

): PredicateProvider {

  override val predicate: Predicate? get() {
    return approvalNumber?.let { cardUsageEntity.approvalNumber.`in`(it) }
  }
}
