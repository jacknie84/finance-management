package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import best.jacknie.finance.common.card.domain.QCardUsageFileEntity.cardUsageFileEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Suppress("unused")
class CardUsageFileCustomRepositoryImpl: PagingRepositorySupport(CardUsageFileEntity::class), CardUsageFileCustomRepository {

  override fun findAll(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity> {
    return getPage(cardUsageFileEntity, pageable) { cardUsageFileEntity.card.id.eq(cardId) }
  }
}
