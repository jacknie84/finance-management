package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpendingLogServiceImpl(
  private val logOutPort: SpendingLogOutPort,
  private val logTagOutPort: SpendingLogTagOutPort,
  private val userOutPort: UserOutPort,
): SpendingLogService {

  @Transactional(readOnly = true)
  override fun getSpendingLogsPage(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLog> {
    val page = logOutPort.findAll(filter, pageable)
    val tagsMap = getSpendingLogTagsMap(page)
    return page.map { toSpendingLog(it, tagsMap[it.id!!]) }
  }

  @Transactional
  override fun createSpendingLog(dto: SaveSpendingLog): SpendingLog {
    val user = userOutPort.getOrCreateUser(dto.username)
    val log =  logOutPort.create(dto, user)
    val tags = logTagOutPort.replaceAll(log, dto.tags)
    return toSpendingLog(log, tags)
  }

  @Transactional
  override fun getSpendingLog(id: Long): SpendingLog {
    val log = logOutPort.findById(id) ?: notFound(id)
    val tags = logTagOutPort.findAllByLogId(log.id!!)
    return toSpendingLog(log, tags)
  }

  @Transactional
  override fun putSpendingLog(id: Long, dto: SaveSpendingLog) {
    val log = logOutPort.findById(id) ?: notFound(id)
    val user = userOutPort.getOrCreateUser(dto.username)
    logOutPort.update(log, dto, user)
    logTagOutPort.replaceAll(log, dto.tags)
  }

  @Transactional
  override fun patchSpendingLog(id: Long, dto: PatchSpendingLog) {
    val log = logOutPort.findById(id) ?: notFound(id)
    val user = dto.username?.let { userOutPort.getOrCreateUser(it) }
    logOutPort.update(log, dto, user)
    logTagOutPort.replaceAll(log, dto.tags)
  }

  @Transactional
  override fun deleteSpendingLog(id: Long) {
    logTagOutPort.deleteAllByLogId(id)
    logOutPort.delete(id)
  }

  @Transactional(readOnly = true)
  override fun getSpendingLogsPreset(): SpendingLogTagsPreset {
    val tags = logTagOutPort.findAllPreset()
    return SpendingLogTagsPreset(tags)
  }

  private fun getSpendingLogTagsMap(page: Page<SpendingLogEntity>): Map<Long, List<SpendingLogTagEntity>> {
    if (page.isEmpty) {
      return emptyMap()
    }
    val ids = page.map { it.id!! }.toSet()
    return logTagOutPort.getMapByLogId(ids)
  }

  private fun toSpendingLog(log: SpendingLogEntity, tags: List<SpendingLogTagEntity>?): SpendingLog {
    return SpendingLog(
      id = log.id!!,
      summary = log.summary,
      amount = log.amount,
      time = log.time.instant,
      tags = tags?.map { it.tag }?.toSet(),
      user = log.user
    )
  }

  private fun notFound(id: Long): Nothing = throw HttpStatusCodeException.NotFound("지출 내역을 찾을 수 없습니다(id: $id)")
}
