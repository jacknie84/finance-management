package best.jacknie.finance.spending.log.application.port

import jakarta.validation.constraints.Positive
import java.time.Instant

data class CardUsagesFilter(

  /**
   * 카드 아이디 목록
   */
  var cardId: Set<@Positive Long>? = null,

  /**
   * 승인 번호 목록
   */
  var approvalNumber: Set<String>? = null,

  /**
   * 검색어 목록
   */
  var search001: Set<String>? = null,

  /**
   * 검색 시작 시간
   */
  var start: Instant?,

  /**
   * 검색 종료 시간
   */
  var end: Instant?,

)
