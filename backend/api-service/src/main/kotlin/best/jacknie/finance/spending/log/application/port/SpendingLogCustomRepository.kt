package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpendingLogCustomRepository {

  /**
   * 지출 내역 목록 페이지 조회
   */
  fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity>

  /**
   * 지출 내역 총 금액
   */
  fun findTotalAmount(filter: SpendingStatisticsFilter): Int

  /**
   * 연별 지출 내역 통계
   */
  fun findYearStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>

  /**
   * 월별 지출 내역 통계
   */
  fun findMonthStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>

  /**
   * 일별 지출 내역 통계
   */
  fun findDayStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>

  /**
   * 요일별 지출 내역 통계
   */
  fun findDowStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>

  /**
   * 시간별 지출 내역 통계
   */
  fun findHourStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>

  /**
   * 사용자별 지출 내역 통계
   */
  fun findUserStatistics(filter: SpendingStatisticsFilter): List<SpendingStatistics.Item>
}
