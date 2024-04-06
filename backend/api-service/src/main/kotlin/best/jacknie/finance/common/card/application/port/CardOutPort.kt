package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardOutPort {

  /**
   * 카드 번호로 카드 정보 조회 여부 확인
   */
  fun existsByNumber(number: String): Boolean

  /**
   * 카드 정보 생성 처리
   */
  fun create(dto: SaveCard, user: UserEntity): CardEntity

  /**
   * 카드 정보 수정 처리
   */
  fun update(entity: CardEntity, dto: SaveCard, user: UserEntity): CardEntity

  /**
   * 카드 정보 수정 처리
   */
  fun update(entity: CardEntity, dto: PatchCard, user: UserEntity?): CardEntity

  /**
   * 카드 목록 페이지 조회
   */
  fun findAll(pageable: Pageable): Page<CardEntity>

  /**
   * 카드 정보 조회
   */
  fun findById(id: Long): CardEntity?

  /**
   * 카드 정보 조회 여부 확인
   */
  fun existsById(id: Long): Boolean
}
