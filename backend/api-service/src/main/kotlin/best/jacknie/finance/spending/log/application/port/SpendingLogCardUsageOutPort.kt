package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogCardUsageEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity

interface SpendingLogCardUsageOutPort {

  /**
   * 지출 내역 카드 내역 매핑 생성 처리
   */
  fun create(log: SpendingLogEntity, cardUsageId: Long): SpendingLogCardUsageEntity

  /**
   * 지출 내역 카드 내역 매핑 조회
   */
  fun findByCardId(cardId: Long): SpendingLogCardUsageEntity?
}
