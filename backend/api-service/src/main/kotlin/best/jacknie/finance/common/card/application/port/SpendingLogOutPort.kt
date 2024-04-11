package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.spending.log.application.port.SaveSpendingLog

interface SpendingLogOutPort {

  /**
   * 지출 내역 생성 처리
   */
  fun createSpendingLog(dto: SaveSpendingLog, cardId: Long)

  /**
   * 지출 내역 수정 처리
   */
  fun updateSpendingLog(dto: SaveSpendingLog, cardId: Long)
}
