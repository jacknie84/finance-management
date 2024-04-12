package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.spending.log.adapter.persistence.jpa.SpendingLogRepository
import best.jacknie.finance.spending.log.application.port.PatchSpendingLog
import best.jacknie.finance.spending.log.application.port.SaveSpendingLog
import best.jacknie.finance.spending.log.application.port.SpendingLogOutPort
import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Component
class SpendingLogPersistenceAdapter(
  private val logRepository: SpendingLogRepository,
): SpendingLogOutPort {

  @Transactional
  override fun create(dto: SaveSpendingLog, user: UserEntity): SpendingLogEntity {
    val entity = SpendingLogEntity(
      summary = dto.summary,
      amount = dto.amount,
      time = getSpendingTime(dto.time),
      user = user,
    )
    return logRepository.save(entity)
  }

  @Transactional
  override fun update(entity: SpendingLogEntity, dto: SaveSpendingLog, user: UserEntity): SpendingLogEntity {
    entity.apply {
      amount = dto.amount
      time = getSpendingTime(dto.time)
      this.user = user
    }
    return logRepository.save(entity)
  }

  @Transactional
  override fun update(entity: SpendingLogEntity, dto: PatchSpendingLog, user: UserEntity?): SpendingLogEntity {
    entity.apply {
      dto.amount?.let { amount = it }
      dto.time?.let { time = getSpendingTime(it) }
      user?.let { this.user = it }
    }
    return logRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity> {
    return logRepository.findAll(filter, pageable)
  }

  @Transactional(readOnly = true)
  override fun findById(id: Long): SpendingLogEntity? {
    return logRepository.findByIdOrNull(id)
  }

  @Transactional
  override fun delete(id: Long) {
    return logRepository.deleteById(id)
  }

  private fun getSpendingTime(time: ZonedDateTime): SpendingTime {
    return SpendingTime(
      instant = time.toInstant(),
      zone = time.zone,
      year = time.year,
      month = time.month,
      dayOfMonth = time.dayOfMonth,
      dayOfWeek = time.dayOfWeek,
      hour = time.hour,
    )
  }
}
