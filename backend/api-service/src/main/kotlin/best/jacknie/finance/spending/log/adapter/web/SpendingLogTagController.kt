package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.SpendingLogTagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/spending/log/tags")
class SpendingLogTagController(
  private val tagService: SpendingLogTagService,
) {

  @GetMapping("/preset")
  fun getSpendingLogsPreset(): ResponseEntity<*> {
    val preset = tagService.getSpendingLogTagsPreset()
    return ResponseEntity.ok(preset)
  }
}
