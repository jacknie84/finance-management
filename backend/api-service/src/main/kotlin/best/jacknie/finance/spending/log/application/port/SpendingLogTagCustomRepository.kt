package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity

interface SpendingLogTagCustomRepository {

  /**
   * 지출 내역 태그 목록 프리셋 조회
   */
  fun findAllPreset(): List<String>

  /**
   * 지출 내역 내용으로 추천 되는 지출 내역 태그 목록 조회
   */
  fun findAllRecommended(summary: String): List<SpendingLogTagEntity>
}
