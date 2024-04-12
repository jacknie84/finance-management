package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpendingLogTagRepository: JpaRepository<SpendingLogTagEntity, SpendingLogTagEntity.PrimaryKey>, SpendingLogTagCustomRepository {

  /**
   * 지출 내역 태그 목록 조회
   */
  fun findAllByLogIdIn(logIds: Set<Long>): List<SpendingLogTagEntity>

  /**
   * 지출 내역 태그 목록 조회
   */
  fun findAllByLogId(logId: Long): List<SpendingLogTagEntity>
}
