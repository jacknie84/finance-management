package best.jacknie.finance.spending.log.application.port

interface SpendingLogTagService {

  /**
   * 지출 내역 태그 프리셋 조회
   */
  fun getSpendingLogTagsPreset(): SpendingLogTagsPreset
}
