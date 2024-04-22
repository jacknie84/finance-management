package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardUsageRepository: JpaRepository<CardUsageEntity, Long>, CardUsageCustomRepository
