package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpendingLogServiceImpl(
  private val logRepository: SpendingLogRepository,
  private val tagRepository: SpendingLogTagRepository,
  private val userClient: UserClient,
): SpendingLogService {

  @Transactional(readOnly = true)
  override fun getSpendingLogsPage(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLog> {
    val page = logRepository.findAll(filter, pageable)
    val tagsMap = tagRepository.findMapByLogId(page)
    return page.map { SpendingLog.from(it, tagsMap[it.id!!]) }
  }

  @Transactional
  override fun createSpendingLog(dto: SaveSpendingLog): SpendingLog {
    val user = userClient.getOrCreateUser(dto.username)
    val log = logRepository.save(SpendingLogEntity(
      summary = dto.summary,
      amount = dto.amount,
      time = getSpendingTime(dto.time),
      user = user,
    ))
    val tags = tagRepository.replaceAll(log, dto.tags)
    return SpendingLog.from(log, tags)
  }

  @Transactional
  override fun getSpendingLog(id: Long): SpendingLog {
    val log = logRepository.findByIdOrNull(id) ?: notFound(id)
    val tags = tagRepository.findAllByLogId(log.id!!)
    return SpendingLog.from(log, tags)
  }

  @Transactional
  override fun putSpendingLog(id: Long, dto: SaveSpendingLog) {
    var log = logRepository.findByIdOrNull(id) ?: notFound(id)
    log.apply {
      summary = dto.summary
      amount = dto.amount
      time = getSpendingTime(dto.time)
      user = userClient.getOrCreateUser(dto.username)
    }
    log = logRepository.save(log)
    tagRepository.replaceAll(log, dto.tags)
  }

  @Transactional
  override fun patchSpendingLog(id: Long, dto: PatchSpendingLog) {
    var log = logRepository.findByIdOrNull(id) ?: notFound(id)
    val user = dto.username?.let { userClient.getOrCreateUser(it) }
    log.apply {
      dto.amount?.let { amount = it }
      dto.time?.let { time = getSpendingTime(it) }
      user?.let { this.user = it }
    }
    log = logRepository.save(log)
    tagRepository.replaceAll(log, dto.tags)
  }

  @Transactional
  override fun deleteSpendingLog(id: Long) {
    tagRepository.deleteAllByLogId(id)
    logRepository.deleteById(id)
  }

  private fun notFound(id: Long): Nothing = throw HttpStatusCodeException.NotFound("지출 내역을 찾을 수 없습니다(id: $id)")
}
