package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardService {

  /**
   * 카드 정보 생성 처리
   */
  fun createCard(dto: SaveCard): CardEntity

  /**
   * 카드 목록 페이지 조회
   */
  fun getCardsPage(pageable: Pageable): Page<CardEntity>

  /**
   * 카드 정보 조회
   */
  fun getCard(id: Long): CardEntity

  /**
   * 카드 정보 수정 처리
   */
  fun putCard(id: Long, dto: SaveCard)

  /**
   * 카드 정보 수정 처리
   */
  fun patchCard(id: Long, dto: PatchCard)

}
