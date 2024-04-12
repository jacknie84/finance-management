package best.jacknie.finance.spending.log.adapter.persistence.jpa

interface SpendingLogTagCustomRepository {

  /**
   * 지출 내역 태그 목록 프리셋 조회
   */
  fun findAllPreset(): List<String>
}
