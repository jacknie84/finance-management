package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.card.domain.CardUsageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageOutPort {

  /**
   * 카드 사용 내역 생성 처리
   */
  fun create(dto: SaveCardUsage, card: CardEntity): CardUsageEntity

  /**
   * 승인 번호로 카드 사용 내역 조회 여부 확인
   */
  fun existsByApprovalNumber(approvalNumber: String): Boolean

  /**
   * 카드 사용 내역 조회
   */
  fun findOne(cardId: Long, id: Long): CardUsageEntity?

  /**
   * 카드 사용 내역 수정 처리
   */
  fun update(entity: CardUsageEntity, dto: SaveCardUsage): CardUsageEntity

  /**
   * 카드 사용 내역 목록 페이지 조회
   */
  fun findAll(cardId: Long, filter: CardUsagesFilter, pageable: Pageable): Page<CardUsageEntity>
}
