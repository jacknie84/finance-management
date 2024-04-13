package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.CardUsageService
import best.jacknie.finance.spending.log.application.port.CardUsagesFilter
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("spendingLogCardController")
@RequestMapping("/v1/card")
class CardController(
  private val usageService: CardUsageService,
) {

  @GetMapping("/usages")
  fun getCardUsagesPage(@Valid filter: CardUsagesFilter, pageable: Pageable): ResponseEntity<*> {
    val page = usageService.getCardUsagesPage(filter, pageable)
    return ResponseEntity.ok(page)
  }
}
