package best.jacknie.finance.spending.log.adapter.export

import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import best.jacknie.finance.spending.log.application.port.SpendingLogService
import org.springframework.stereotype.Service

@Service
class SpendingLogExportedService(
  private val logService: SpendingLogService,
) {

  /**
   * 지출 내역 생성 처리
   */
  fun createSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    logService.createSpendingLog(dto, cardId)
  }

  /**
   * 지출 내역 수정 처리
   */
  fun updateSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    logService.updateSpendingLog(dto, cardId);
  }
}
