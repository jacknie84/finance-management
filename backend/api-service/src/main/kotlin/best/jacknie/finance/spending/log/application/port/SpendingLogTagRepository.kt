package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository

interface SpendingLogTagRepository: JpaRepository<SpendingLogTagEntity, SpendingLogTagEntity.PrimaryKey>,
    SpendingLogTagCustomRepository {

  /**
   * 지출 내역 태그 목록 조회
   */
  fun findAllByLogIdIn(logIds: Set<Long>): List<SpendingLogTagEntity>

  /**
   * 지출 내역 태그 목록 조회
   */
  fun findAllByLogId(logId: Long): List<SpendingLogTagEntity>

  /**
   * 지출 내역 태그 치환 처리
   */
  fun replaceAll(log: SpendingLogEntity, tagValues: Set<String>?): List<SpendingLogTagEntity> {
    val id = log.id!!
    val tags = tagValues?.map { SpendingLogTagEntity(id, it, log) }
    deleteAllByLogId(id)
    return if (tags.isNullOrEmpty()) emptyList() else saveAll(tags)
  }

  /**
   * 지출 내역에 대한 태그 목록 삭제 처리
   */
  fun deleteAllByLogId(id: Long) {
    findAllByLogId(id).forEach { delete(it) }
  }

  /**
   * 지출 내역 맵 조회
   */
  fun findMapByLogId(page: Page<SpendingLogEntity>): Map<Long, List<SpendingLogTagEntity>> {
    if (page.isEmpty) {
      return emptyMap()
    }
    val logIds = page.map { it.id!! }.toSet()
    return findMapByLogId(logIds)
  }

  /**
   * 지출 내역 맵 조회
   */
  fun findMapByLogId(logIds: Set<Long>): Map<Long, List<SpendingLogTagEntity>> {
    val entities = findAllByLogIdIn(logIds)
    return entities.groupBy { it.logId }
  }

  /**
   * 지출 내역 맵을 조회 하거나 치환 처리
   */
  fun findOrReplaceAll(log: SpendingLogEntity, tags: Set<String>?): List<SpendingLogTagEntity> {
    return if (tags.isNullOrEmpty()) findAllByLogId(log.id!!) else replaceAll(log, tags)
  }
}
