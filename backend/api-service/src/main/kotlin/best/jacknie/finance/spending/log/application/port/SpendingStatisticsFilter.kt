package best.jacknie.finance.spending.log.application.port

import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class SpendingStatisticsFilter(

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
)
