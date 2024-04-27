package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.spending.log.application.port.*
import org.springframework.stereotype.Service

@Service
class SpendingStatisticsServiceImpl(
  private val logRepository: SpendingLogRepository,
  private val tagRepository: SpendingLogTagRepository,
): SpendingStatisticsService {

  override fun getSpendingStatistics(
    subject: SpendingStatistics.Subject,
    filter: SpendingStatisticsFilter
  ): SpendingStatistics {
    val totalAmount = logRepository.findTotalAmount(filter)
    val items = when(subject) {
      SpendingStatistics.Subject.YEAR -> logRepository.findYearStatistics(filter)
      SpendingStatistics.Subject.MONTH -> logRepository.findMonthStatistics(filter)
      SpendingStatistics.Subject.DAY_OF_MONTH -> logRepository.findDayStatistics(filter)
      SpendingStatistics.Subject.DAY_OF_WEEK -> logRepository.findDowStatistics(filter)
      SpendingStatistics.Subject.HOUR -> logRepository.findHourStatistics(filter)
      SpendingStatistics.Subject.TAG -> tagRepository.findSpendingStatistics(filter)
      SpendingStatistics.Subject.USER -> logRepository.findUserStatistics(filter)
    }
    return SpendingStatistics(subject, totalAmount, items)
  }
}
