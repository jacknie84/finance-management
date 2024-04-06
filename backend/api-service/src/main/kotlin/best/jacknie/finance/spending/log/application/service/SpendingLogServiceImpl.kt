package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.common.user.adapter.export.ExportedUserService
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpendingLogServiceImpl(
  private val logOutPort: SpendingLogOutPort,
  private val userService: ExportedUserService,
): SpendingLogService {

  @Transactional(readOnly = true)
  override fun getSpendingLogsPage(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity> {
    return logOutPort.findAll(filter, pageable)
  }

  @Transactional
  override fun createSpendingLog(dto: SaveSpendingLog): SpendingLogEntity {
    val user = userService.getOrCreateUser(dto.username)
    return logOutPort.create(dto, user)
  }

  @Transactional
  override fun getSpendingLog(id: Long): SpendingLogEntity {
    return logOutPort.findById(id) ?: throw HttpStatusCodeException.NotFound("지출 내역을 찾을 수 없습니다(id: $id)")
  }

  @Transactional
  override fun putSpendingLog(id: Long, dto: SaveSpendingLog) {
    val entity = getSpendingLog(id)
    val user = userService.getOrCreateUser(dto.username)
    logOutPort.update(entity, dto, user)
  }

  @Transactional
  override fun patchSpendingLog(id: Long, dto: PatchSpendingLog) {
    val entity = getSpendingLog(id)
    val user = dto.username?.let { userService.getOrCreateUser(it) }
    logOutPort.update(entity, dto, user)
  }

  @Transactional
  override fun deleteSpendingLog(id: Long) {
    logOutPort.delete(id)
  }
}
