package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.PatchSpendingLog
import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import best.jacknie.finance.spending.log.application.port.SpendingLogService
import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1/spending/logs")
class SpendingLogController(
  private val logService: SpendingLogService,
) {

  @PostMapping
  fun createSpendingLog(@RequestBody @Valid dto: SaveSpendingLog, uri: UriComponentsBuilder): ResponseEntity<*> {
    val entity = logService.createSpendingLog(dto)
    val location = uri.path("/spending/logs/{id}").buildAndExpand(entity.id).toUri()
    return ResponseEntity.created(location).build<Any>()
  }

  @GetMapping
  fun getSpendingLogsPage(@Valid filter: SpendingLogsFilter, pageable: Pageable): ResponseEntity<*> {
    val page = logService.getSpendingLogsPage(filter, pageable)
    return ResponseEntity.ok(page)
  }

  @GetMapping("/{id}")
  fun getSpendingLog(@PathVariable("id") id: Long): ResponseEntity<*> {
    val entity = logService.getSpendingLog(id)
    return ResponseEntity.ok(entity)
  }

  @PutMapping("/{id}")
  fun putSpendingLog(@PathVariable("id") id: Long, @RequestBody @Valid dto: SaveSpendingLog): ResponseEntity<*> {
    logService.putSpendingLog(id, dto)
    return ResponseEntity.noContent().build<Any>()
  }

  @PatchMapping("/{id}")
  fun patchSpendingLog(@PathVariable("id") id: Long, @RequestBody @Valid dto: PatchSpendingLog): ResponseEntity<*> {
    logService.patchSpendingLog(id, dto)
    return ResponseEntity.noContent().build<Any>()
  }

  @DeleteMapping("/{id}")
  fun deleteSpendingLog(@PathVariable("id") id: Long): ResponseEntity<*> {
    logService.deleteSpendingLog(id)
    return ResponseEntity.noContent().build<Any>()
  }
}
