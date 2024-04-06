package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.QCardUsageEntity.cardUsageEntity
import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
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
