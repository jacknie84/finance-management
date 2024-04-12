package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.domain.QCardUsageFileEntity.cardUsageFileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Suppress("unused")
class CardUsageFileCustomRepositoryImpl: PagingRepositorySupport(CardUsageFileEntity::class),
  CardUsageFileCustomRepository {

  override fun findAll(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity> {
    return getPage(cardUsageFileEntity, pageable) { cardUsageFileEntity.card.id.eq(cardId) }
  }
}
