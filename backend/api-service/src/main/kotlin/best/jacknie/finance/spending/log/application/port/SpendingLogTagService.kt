package best.jacknie.finance.spending.log.application.port

interface SpendingLogTagService {

  /**
   * 지출 내역 태그 프리셋 조회
   */
  fun getSpendingLogTagsPreset(): SpendingLogTagsPreset

  /**
   * 지출 내역 내용으로 추천 되는 지출 내역 태그 목록 조회
   */
  fun getRecommendedSpendingLogTags(summary: String?): Set<String>?
}
