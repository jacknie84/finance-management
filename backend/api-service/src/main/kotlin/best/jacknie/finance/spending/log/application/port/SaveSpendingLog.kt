package best.jacknie.finance.spending.log.application.port

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

data class SaveSpendingLog(

  /**
   * 소비 내용
   */
  @field:Size(max = 200)
  var summary: String,

  /**
   * 소비 금액
   */
  @field:Positive
  var amount: Int,

  /**
   * 소비 시간
   */
  var time: ZonedDateTime,

  /**
   * 태그 목록
   */
  var tags: MutableSet<@NotBlank @Size(min = 3, max = 200) @Pattern(regexp = "(\\w|[가-힣])+") String>? = null,

  /**
   * 지출 사용자 이름
   */
  @field:NotBlank
  @field:Size(max = 200)
  var username: String,

)
