package best.jacknie.finance.spending.log.adapter.scheduling

import best.jacknie.finance.core.utils.pagination.PageStream
import best.jacknie.finance.core.utils.pagination.SpringDataPager
import best.jacknie.finance.spending.log.application.port.PatchSpendingLog
import best.jacknie.finance.spending.log.application.port.SpendingLogService
import best.jacknie.finance.spending.log.application.port.SpendingLogTagService
import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SpendingLogTagScheduling(
  private val logService: SpendingLogService,
  private val tagService: SpendingLogTagService,
) {

  @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
  fun executeSpendingLogTaggingAutomation() {
    val conditions = listOf(SpendingLogsFilter.condition(SpendingLogsFilter.QueryCondition.EMPTY_TAGS))
    val filter = SpendingLogsFilter(conditions = conditions)
    val pageable = PageRequest.of(0, 100, Direction.DESC, "id")
    val pageStream = PageStream(SpringDataPager(pageable) { logService.getSpendingLogsPage(filter, it) })
    for (log in pageStream) {
      val tags = tagService.getRecommendedSpendingLogTags(log.summary) ?: emptySet()
      val patch = PatchSpendingLog(tags = mutableSetOf(*tags.toTypedArray()))
      logService.patchSpendingLog(log.id, patch)
    }
  }
}
