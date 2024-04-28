package best.jacknie.finance.spending.log.application.port

import java.time.Instant
import java.time.ZoneId

data class SaveSpendingTime(

  /**
   * 지출 시간
   */
  var instant: Instant,

  /**
   * 타임 존 아이디
   */
  var zone: ZoneId,
)
