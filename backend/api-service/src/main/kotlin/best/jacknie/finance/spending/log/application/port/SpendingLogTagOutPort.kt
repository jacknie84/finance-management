package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity

interface SpendingLogTagOutPort {

  /**
   * 지출 내역 태그 프리셋 조회
   */
  fun findAllPreset(): List<String>

  /**
   * 지출 내역 태그 목록 생성
   */
  fun replaceAll(log: SpendingLogEntity, tags: Set<String>?): List<SpendingLogTagEntity>

  /**
   * 지출 내역 태그 목록 맵 조회
   */
  fun getMapByLogId(logIds: Set<Long>): Map<Long, List<SpendingLogTagEntity>>

  /**
   * 지출 내역 태그 목록 조회
   */
  fun findAllByLogId(logId: Long): List<SpendingLogTagEntity>

  /**
   * 지출 내역 태그 목록 삭제
   */
  fun deleteAllByLogId(logId: Long)
}
