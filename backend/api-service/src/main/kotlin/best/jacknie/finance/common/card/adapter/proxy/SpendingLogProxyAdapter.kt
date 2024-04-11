package best.jacknie.finance.common.card.adapter.proxy

import best.jacknie.finance.common.card.application.port.SpendingLogOutPort
import best.jacknie.finance.spending.log.adapter.export.SpendingLogExportedService
import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SpendingLogProxyAdapter(
  private val logExportedService: SpendingLogExportedService,
): SpendingLogOutPort {

  @Transactional
  override fun createSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    logExportedService.createSpendingLog(dto, cardId)
  }

  @Transactional
  override fun updateSpendingLog(dto: SaveSpendingLog, cardId: Long) {
    logExportedService.createSpendingLog(dto, cardId)
  }
}
