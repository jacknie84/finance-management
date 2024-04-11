package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpendingLogCustomRepository {

  /**
   * 지출 내역 목록 페이지 조회
   */
  fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity>
}
