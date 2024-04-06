package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.application.port.CardUsagesFilter
import best.jacknie.finance.common.card.domain.CardUsageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageCustomRepository {

  /**
   * 승인 번호로 카드 사용 내역 조회 여부 확인
   */
  fun existsByApprovalNumber(approvalNumber: String): Boolean

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>
}
