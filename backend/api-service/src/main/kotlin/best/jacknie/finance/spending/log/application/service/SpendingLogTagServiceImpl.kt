package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.spending.log.application.port.SpendingLogTagOutPort
import best.jacknie.finance.spending.log.application.port.SpendingLogTagService
import best.jacknie.finance.spending.log.application.port.SpendingLogTagsPreset
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpendingLogTagServiceImpl(
  private val logTagOutPort: SpendingLogTagOutPort,
): SpendingLogTagService {

  @Transactional(readOnly = true)
  override fun getSpendingLogsPreset(): SpendingLogTagsPreset {
    val tags = logTagOutPort.findAllPreset()
    return SpendingLogTagsPreset(tags)
  }
}
