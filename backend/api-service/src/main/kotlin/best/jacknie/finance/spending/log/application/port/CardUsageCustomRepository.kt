package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageCustomRepository {

  /**
   * 카드 사용 내역 조회
   */
  fun findByCardIdAndId(cardId: Long, id: Long): CardUsageEntity?

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun findAll(filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>
}
