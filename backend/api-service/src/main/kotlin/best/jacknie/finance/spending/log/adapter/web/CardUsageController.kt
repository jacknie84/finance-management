package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.CardUsageService
import best.jacknie.finance.spending.log.application.port.CardUsagesFilter
import best.jacknie.finance.spending.log.application.port.SaveCardUsage
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1/cards/{cardId}/usages")
class CardUsageController(
  private val usageService: CardUsageService
) {

  @PostMapping
  fun createCardUsage(
    @PathVariable("cardId") cardId: Long,
    @RequestBody @Valid dto: SaveCardUsage,
    uri: UriComponentsBuilder
  ): ResponseEntity<*> {
    val entity = usageService.createCardUsage(cardId, dto)
    val location = uri.path("/{id}").buildAndExpand(cardId, entity.id).toUri()
    return ResponseEntity.created(location).build<Any>()
  }

  @GetMapping
  fun getCardUsagesPage(
    @PathVariable("cardId") cardId: Long,
    @Valid filter: CardUsagesFilter,
    pageable: Pageable,
  ): ResponseEntity<*> {
    val page = usageService.getCardUsagesPage(cardId, filter, pageable)
    return ResponseEntity.ok(page)
  }

  @PutMapping("/{id}")
  fun putCardUsage(
    @PathVariable("cardId") cardId: Long,
    @PathVariable("id") id: Long,
    @RequestBody @Valid dto: SaveCardUsage,
  ): ResponseEntity<*> {
    usageService.putCardUsage(cardId, id, dto)
    return ResponseEntity.noContent().build<Any>()
  }
}
