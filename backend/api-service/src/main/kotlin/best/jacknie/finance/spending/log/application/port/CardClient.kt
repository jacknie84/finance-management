package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.common.card.domain.CardEntity

interface CardClient {

  /**
   * 카드 정보 조회
   */
  fun getCard(cardId: Long): CardEntity
}
