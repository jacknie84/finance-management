package best.jacknie.finance.spending.log.application.port

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

data class PatchSpendingLog(

  /**
   * 지출 금액
   */
  @field:Positive
  var amount: Int? = null,

  /**
   * 지출 시간
   */
  var time: ZonedDateTime? = null,

  /**
   * 태그 목록
   */
  var tags: MutableSet<@NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "(\\w|[가-힣])+") String>? = null,

  /**
   * 지출 사용자 이름
   */
  @field:Size(max = 200)
  var username: String? = null,
)
