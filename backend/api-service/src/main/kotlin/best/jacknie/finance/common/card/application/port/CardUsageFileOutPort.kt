package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageFileOutPort {

  /**
   * 카드 사용 내역 파일 정보 생성 처리
   */
  fun create(dto: SaveCardUsageFile, card: CardEntity): CardUsageFileEntity

  /**
   * 카드 사용 내역 파일 목록 페이지 조회
   */
  fun findAll(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity>

  /**
   * 카드 사용 내역 파일 정보 조회
   */
  fun findOne(cardId: Long, id: Long): CardUsageFileEntity?
}
