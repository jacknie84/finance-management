package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.SpendingLogTagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/spending/log/tags")
class SpendingLogTagController(
  private val tagService: SpendingLogTagService,
) {

  @GetMapping("/preset")
  fun getSpendingLogTagsPreset(): ResponseEntity<*> {
    val tags = tagService.getSpendingLogTagsPreset()
    return ResponseEntity.ok(tags)
  }

  @GetMapping("/recommended")
  fun getSpendingLogTagsRecommended(@RequestParam(name = "summary", required = false) summary: String?): ResponseEntity<*> {
    val tags = tagService.getRecommendedSpendingLogTags(summary)
    return ResponseEntity.ok(tags)
  }
}
