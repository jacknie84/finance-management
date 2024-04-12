package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity

@Suppress("unused")
class SpendingLogTagCustomRepositoryImpl: PagingRepositorySupport(SpendingLogTagEntity::class), SpendingLogTagCustomRepository {

  override fun findAllPreset(): List<String> {
    return from(spendingLogTagEntity).select(spendingLogTagEntity.tag).groupBy(spendingLogTagEntity.tag).fetch()
  }
}
