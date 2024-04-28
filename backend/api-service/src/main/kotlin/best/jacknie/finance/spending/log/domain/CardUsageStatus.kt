package best.jacknie.finance.spending.log.domain

enum class CardUsageStatus {

  /**
   * 전표 미매입
   */
  NOT_INVOICED {
    override val positive = true
  },

  /**
   * 전표 매입
   */
  INVOICED {
    override val positive = true
  },

  /**
   * 취소 전표 매입
   */
  CANCELLED_INVOICED {
    override val positive = false
  },

  /**
   * 승인 취소
   */
  APPROVAL_CANCELLED {
    override val positive = false
  };

  /**
   * 카드 사용 금액이 양수 인지 확인
   */
  abstract val positive: Boolean
}
