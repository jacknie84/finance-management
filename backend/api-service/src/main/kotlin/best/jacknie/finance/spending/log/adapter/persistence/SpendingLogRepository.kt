package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpendingLogRepository: JpaRepository<SpendingLogEntity, Long>, SpendingLogCustomRepository {}
