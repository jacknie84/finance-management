package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.core.web.http.binary
import best.jacknie.finance.spending.log.application.port.CardUsageFileService
import best.jacknie.finance.spending.log.application.port.SaveCardUsageFile
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1/cards/{cardId}/usage/files")
class CardUsageFileController(
  private val fileService: CardUsageFileService
) {

  @PostMapping
  fun createCardUsageFile(
    @PathVariable("cardId") cardId: Long,
    @RequestBody @Valid dto: SaveCardUsageFile,
    uri: UriComponentsBuilder
  ): ResponseEntity<*> {
    val entity = fileService.createCardUsageFile(cardId, dto)
    val location = uri.path("/v1/cards/{cardId}/usage/files/{id}").buildAndExpand(cardId, entity.id).toUri()
    return ResponseEntity.created(location).build<Any>()
  }

  @GetMapping
  fun getCardUsageFilesPage(@PathVariable("cardId") cardId: Long, pageable: Pageable): ResponseEntity<*> {
    val page = fileService.getCardUsageFilesPage(cardId, pageable)
    return ResponseEntity.ok(page)
  }

  @GetMapping("/{id}")
  fun getCardUsageFile(
    @PathVariable("cardId") cardId: Long,
    @PathVariable("id") id: Long,
  ): ResponseEntity<*> {
    val fileObject = fileService.getCardUsageFileObject(cardId, id)
    return ResponseEntity.ok().binary(fileObject)
  }
}
