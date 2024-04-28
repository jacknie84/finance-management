package best.jacknie.finance.common.card.adapter.web

import best.jacknie.finance.common.card.application.port.CardService
import best.jacknie.finance.common.card.application.port.PatchCard
import best.jacknie.finance.common.card.application.port.SaveCard
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1/cards")
class CardController(
  private val cardService: CardService,
) {

  @PostMapping
  fun createCard(@RequestBody @Valid dto: SaveCard, uri: UriComponentsBuilder): ResponseEntity<*> {
    val entity = cardService.createCard(dto)
    val location = uri.path("/v1/cards/{id}").buildAndExpand(entity.id).toUri()
    return ResponseEntity.created(location).build<Any>()
  }

  @GetMapping
  fun getCardsPage(pageable: Pageable): ResponseEntity<*> {
    val page = cardService.getCardsPage(pageable)
    return ResponseEntity.ok(page)
  }

  @GetMapping("/{id}")
  fun getCard(@PathVariable("id") id: Long): ResponseEntity<*> {
    val entity = cardService.getCard(id)
    return ResponseEntity.ok(entity)
  }

  @PutMapping("/{id}")
  fun putCard(@PathVariable("id") id: Long, @RequestBody @Valid dto: SaveCard): ResponseEntity<*> {
    cardService.putCard(id, dto)
    return ResponseEntity.noContent().build<Any>()
  }

  @PatchMapping("/{id}")
  fun patchCard(@PathVariable("id") id: Long, @RequestBody @Valid dto: PatchCard): ResponseEntity<*> {
    cardService.patchCard(id, dto)
    return ResponseEntity.noContent().build<Any>()
  }
}
