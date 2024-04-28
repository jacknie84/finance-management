package best.jacknie.finance.spending.log.application.port

interface SpendingStatisticsService {

  /**
   * 지출 내역 통계 조회
   */
  fun getSpendingStatistics(subject: SpendingStatistics.Subject, filter: SpendingStatisticsFilter): SpendingStatistics
}
