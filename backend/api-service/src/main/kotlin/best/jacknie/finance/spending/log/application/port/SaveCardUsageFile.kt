package best.jacknie.finance.spending.log.application.port

import jakarta.validation.constraints.Size

data class SaveCardUsageFile(

  /**
   * 파일 설명
   */
  @field:Size(max = 200)
  var description: String?,

  /**
   * 업로드 파일 키
   */
  @field:Size(max = 200)
  var fileKey: String,

  /**
   * 업로드 파일 정책
   */
  var policy: String,
)
