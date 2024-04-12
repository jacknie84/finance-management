package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpendingLogOutPort {

  /**
   * 지출 내역 생성 처리
   */
  fun create(dto: SaveSpendingLog, user: UserEntity): SpendingLogEntity

  /**
   * 지출 내역 생성 처리
   */
  fun create(dto: SaveCardUsage, user: UserEntity): SpendingLogEntity

  /**
   * 지출 내역 수정 처리
   */
  fun update(entity: SpendingLogEntity, dto: SaveSpendingLog, user: UserEntity): SpendingLogEntity

  /**
   * 지출 내역 수정 처리
   */
  fun update(entity: SpendingLogEntity, dto: SaveCardUsage, user: UserEntity): SpendingLogEntity

  /**
   * 지출 내역 수정 처리
   */
  fun update(entity: SpendingLogEntity, dto: PatchSpendingLog, user: UserEntity?): SpendingLogEntity

  /**
   * 지출 내역 목록 페이지 조회
   */
  fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity>

  /**
   * 지출 내역 조회
   */
  fun findById(id: Long): SpendingLogEntity?

  /**
   * 지출 내역 삭제 처리
   */
  fun delete(id: Long)
}
