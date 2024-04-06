package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardIssuer
import jakarta.validation.constraints.Size

data class PatchCard(

  /**
   * 카드 이름
   */
  @field:Size(max = 200)
  var name: String? = null,

  /**
   * 카드 번호
   */
  @field:Size(max = 200)
  var number: String? = null,

  /**
   * 카드 발생사
   */
  var issuer: CardIssuer? = null,

  /**
   * 카드 사용자 이름
   */
  @field:Size(max = 200)
  var username: String? = null,
)
