package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository: JpaRepository<CardEntity, Long>
