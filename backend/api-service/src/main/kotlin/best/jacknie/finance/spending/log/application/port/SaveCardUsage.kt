package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageStatus
import jakarta.validation.Valid
import jakarta.validation.constraints.*

data class SaveCardUsage(

  /**
   * 승인 번호
   */
  @field:NotBlank
  @field:Size(max = 200)
  var approvalNumber: String,

  /**
   * 가맹점 이름
   */
  @field:NotBlank
  @field:Size(max = 200)
  var merchant: String,

  /**
   * 이용 내역 상태
   */
  var status: CardUsageStatus,

  /**
   * 지출 금액
   */
  @field:PositiveOrZero
  var amount: Int,

  /**
   * 지출 시간
   */
  @field:Valid
  var time: SaveSpendingTime,

  /**
   * 태그 목록
   */
  var tags: Set<@NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "(\\w|[가-힣])+") String>?,

  /**
   * 업로드 카드 내역 파일 아이디
   */
  @field:Positive
  var fileId: Long,
)
