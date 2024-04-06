package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpendingLogService {

  /**
   * 지출 내역 생성 처리
   */
  fun createSpendingLog(dto: SaveSpendingLog): SpendingLogEntity

  /**
   * 지출 내역 목록 페이지 조회
   */
  fun getSpendingLogsPage(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity>

  /**
   * 지출 내역 조회
   */
  fun getSpendingLog(id: Long): SpendingLogEntity

  /**
   * 지출 내역 수정 처리
   */
  fun putSpendingLog(id: Long, dto: SaveSpendingLog)

  /**
   * 지출 내역 수정 처리
   */
  fun patchSpendingLog(id: Long, dto: PatchSpendingLog)

  /**
   * 지출 내역 삭제 처리
   */
  fun deleteSpendingLog(id: Long)
}
