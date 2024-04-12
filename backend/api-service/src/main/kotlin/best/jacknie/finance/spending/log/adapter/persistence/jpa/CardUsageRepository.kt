package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.domain.CardUsageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardUsageRepository: JpaRepository<CardUsageEntity, Long>, CardUsageCustomRepository
