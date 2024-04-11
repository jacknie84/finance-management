package best.jacknie.finance.common.card.adapter.persistence.jpa

import best.jacknie.finance.common.card.domain.CardUsageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardUsageRepository: JpaRepository<CardUsageEntity, Long>, CardUsageCustomRepository {

  /**
   * 카드 사용 내역 조회
   */
  fun findByCardIdAndId(cardId: Long, id: Long): CardUsageEntity?
}
