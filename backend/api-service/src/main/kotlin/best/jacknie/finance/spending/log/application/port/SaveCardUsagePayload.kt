package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageStatus

data class SaveCardUsagePayload(

  /**
   * 카드 아이디
   */
  val cardId: Long,

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
   * 카드 내역 파일 아이디
   */
  val fileId: Long,

  /**
   * 지출 내역 아이디
   */
  val logId: Long,
)
