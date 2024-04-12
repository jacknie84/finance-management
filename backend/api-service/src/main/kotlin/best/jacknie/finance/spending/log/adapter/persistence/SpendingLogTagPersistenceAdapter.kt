package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.spending.log.adapter.persistence.jpa.SpendingLogTagRepository
import best.jacknie.finance.spending.log.application.port.SpendingLogTagOutPort
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SpendingLogTagPersistenceAdapter(
  private val tagRepository: SpendingLogTagRepository,
): SpendingLogTagOutPort {

  @Transactional(readOnly = true)
  override fun findAllPreset(): List<String> {
    return tagRepository.findAllPreset()
  }

  @Transactional
  override fun replaceAll(log: SpendingLogEntity, tags: Set<String>?): List<SpendingLogTagEntity> {
    val logId = log.id!!
    val entities = tags?.map { SpendingLogTagEntity(logId, it, log) }
    tagRepository.findAllByLogId(logId).forEach { tagRepository.delete(it) }
    return if (entities.isNullOrEmpty()) {
      emptyList()
    } else {
      tagRepository.saveAll(entities)
    }
  }

  @Transactional(readOnly = true)
  override fun getMapByLogId(logIds: Set<Long>): Map<Long, List<SpendingLogTagEntity>> {
    val entities = tagRepository.findAllByLogIdIn(logIds)
    return entities.groupBy { it.logId }
  }

  @Transactional(readOnly = true)
  override fun findAllByLogId(logId: Long): List<SpendingLogTagEntity> {
    return tagRepository.findAllByLogId(logId)
  }

  @Transactional
  override fun deleteAllByLogId(logId: Long) {
    tagRepository.findAllByLogId(logId).forEach { tagRepository.delete(it) }
  }
}
