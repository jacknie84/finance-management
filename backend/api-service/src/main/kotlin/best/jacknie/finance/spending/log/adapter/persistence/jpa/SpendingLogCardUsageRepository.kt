package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.domain.SpendingLogCardUsageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpendingLogCardUsageRepository: JpaRepository<SpendingLogCardUsageEntity, SpendingLogCardUsageEntity.Id> {

  /**
   * 지출 내역 카드 내역 매핑 정보 조회
   */
  fun findByCardUsageId(cardUsageId: Long): SpendingLogCardUsageEntity?
}
