package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.web.filter.PredefinedCondition
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class SpendingLogsFilter(

  /**
   * 검색 목록
   */
  var search001: Set<@NotBlank String>? = null,

  /**
   * 검색 시작 시간
   */
  var start: Instant? = null,

  /**
   * 검색 종료 시간
   */
  var end: Instant? = null,

  /**
   * 예약 된 검색 조건
   */
  @field:Valid
  var condition: List<PredefinedCondition>? = null,

) {

  companion object {
    /**
     * 지출 내역에 태그가 없는 경우 검색
     */
    const val EMPTY_TAGS = "EMPTY_TAGS"
  }
}
