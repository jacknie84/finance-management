package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.domain.CardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository: JpaRepository<CardEntity, Long>, CardCustomRepository {
}
