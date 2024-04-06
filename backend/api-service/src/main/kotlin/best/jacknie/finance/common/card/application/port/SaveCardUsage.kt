package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardUsageStatus
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

data class SaveCardUsage(

  /**
   * 승인 번호
   */
  @field:Size(max = 200)
  var approvalNumber: String,

  /**
   * 가맹점 이름
   */
  @field:Size(max = 200)
  var merchant: String,

  /**
   * 이용 내역 상태
   */
  var status: CardUsageStatus,

  /**
   * 소비 금액
   */
  @field:Positive
  var amount: Int,

  /**
   * 소비 시간
   */
  var time: ZonedDateTime,
)
