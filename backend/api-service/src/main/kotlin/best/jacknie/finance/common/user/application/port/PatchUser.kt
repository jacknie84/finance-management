package best.jacknie.finance.common.user.application.port

import jakarta.validation.constraints.Size

data class PatchUser(

  /**
   * 출력 이름
   */
  @field:Size(max = 200)
  var displayName: String? = null,

)
