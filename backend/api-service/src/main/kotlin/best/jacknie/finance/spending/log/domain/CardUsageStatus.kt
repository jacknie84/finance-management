package best.jacknie.finance.spending.log.domain

enum class CardUsageStatus {

  /**
   * 전표 미매입
   */
  NOT_INVOICED,

  /**
   * 전표 매입
   */
  INVOICED,

  /**
   * 취소 전표 매입
   */
  CANCELLED_INVOICED,

  /**
   * 승인 취소
   */
  APPROVAL_CANCELLED
}
