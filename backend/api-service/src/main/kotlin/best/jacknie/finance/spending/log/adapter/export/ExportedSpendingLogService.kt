package best.jacknie.finance.spending.log.adapter.export

import best.jacknie.finance.common.user.adapter.export.ExportedUserService
import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import best.jacknie.finance.spending.log.application.port.SpendingLogCardUsageOutPort
import best.jacknie.finance.spending.log.application.port.SpendingLogOutPort
import org.springframework.stereotype.Service

@Service
class ExportedSpendingLogService(
  private val logOutPort: SpendingLogOutPort,
  private val logCardUsageOutPort: SpendingLogCardUsageOutPort,
  private val userService: ExportedUserService,
) {

  /**
   * 지출 내역 생성 처리
   */
  fun createSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    val user = userService.getOrCreateUser(dto.username)
    val log = logOutPort.create(dto, user)
    logCardUsageOutPort.create(log, cardId)
  }

  /**
   * 지출 내역 수정 처리
   */
  fun updateSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    val logCardUsage = logCardUsageOutPort.findByCardId(cardId)
    if (logCardUsage != null) {
      val user = userService.getOrCreateUser(dto.username)
      logOutPort.update(logCardUsage.log, dto, user)
    } else {
      createSpendingLog(dto, cardId)
    }
  }
}
