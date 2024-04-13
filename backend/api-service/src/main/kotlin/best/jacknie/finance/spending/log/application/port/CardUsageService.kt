package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageService {

  /**
   * 카드 사용 내역 생성 처리
   */
  fun createCardUsage(cardId: Long, dto: SaveCardUsage): CardUsageEntity

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun getCardUsagesPage(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun getCardUsagesPage(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>

  /**
   * 카드 사용 내역 수정 처리
   */
  fun putCardUsage(cardId: Long, id: Long, dto: SaveCardUsage)
}
