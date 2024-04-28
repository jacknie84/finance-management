package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageStatus
import java.time.ZonedDateTime

data class RawCardUsage(

  /**
   * 승인 번호
   */
  val approvalNumber: String,

  /**
   * 가맹점 이름
   */
  val merchant: String,

  /**
   * 이용 내역 상태
   */
  val status: CardUsageStatus,

  /**
   * 지출 금액
   */
  val amount: Int,

  /**
   * 지출 시간
   */
  val time: ZonedDateTime,
)
