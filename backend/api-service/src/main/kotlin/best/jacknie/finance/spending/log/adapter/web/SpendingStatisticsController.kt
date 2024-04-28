package best.jacknie.finance.spending.log.adapter.web

import best.jacknie.finance.spending.log.application.port.SpendingStatistics
import best.jacknie.finance.spending.log.application.port.SpendingStatisticsFilter
import best.jacknie.finance.spending.log.application.port.SpendingStatisticsService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/spending/statistics")
class SpendingStatisticsController(
  private val statisticsService: SpendingStatisticsService,
) {

  @GetMapping("/{subject}")
  fun getSpendingStatistics(
    @PathVariable("subject") subject: SpendingStatistics.Subject,
    @Valid filter: SpendingStatisticsFilter,
  ): ResponseEntity<*> {
    val statistics = statisticsService.getSpendingStatistics(subject, filter)
    return ResponseEntity.ok(statistics)
  }
}
