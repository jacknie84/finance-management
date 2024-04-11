package best.jacknie.finance.common.card.adapter.persistence.jpa

interface CardCustomRepository {

  /**
   * 카드 번호로 카드 조회 여부 확인
   */
  fun existsByNumber(number: String): Boolean
}
