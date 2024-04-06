package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageFileCustomRepository {

  /**
   * 카드 사용 내역 파일 목록 페이지 조회
   */
  fun findAll(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity>
}
