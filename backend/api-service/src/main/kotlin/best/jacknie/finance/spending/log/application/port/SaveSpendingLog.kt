package best.jacknie.finance.spending.log.application.port

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class SaveSpendingLog(

  /**
   * 지출 내용
   */
  @field:Size(max = 200)
  var summary: String,

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
  var tags: MutableSet<@NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "(\\w|[가-힣])+") String>? = null,

  /**
   * 지출 사용자 이름
   */
  @field:NotBlank
  @field:Size(max = 200)
  var username: String,

)
