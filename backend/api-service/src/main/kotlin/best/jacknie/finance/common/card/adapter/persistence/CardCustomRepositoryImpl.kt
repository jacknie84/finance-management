package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.card.domain.QCardEntity.cardEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport

@Suppress("unused")
class CardCustomRepositoryImpl: PagingRepositorySupport(CardEntity::class), CardCustomRepository {

  override fun existsByNumber(number: String): Boolean {
    val id = from(cardEntity).select(cardEntity.id)
      .where(cardEntity.number.eq(number))
      .fetchFirst()
    return id != null
  }
}
