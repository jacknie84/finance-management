package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.SpendingLogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/spending/log/tags")
class SpendingLogTagController(
  private val logService: SpendingLogService
) {

  @GetMapping("/preset")
  fun getSpendingLogsPreset(): ResponseEntity<*> {
    val preset = logService.getSpendingLogsPreset()
    return ResponseEntity.ok(preset)
  }
}
