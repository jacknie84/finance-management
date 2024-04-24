package best.jacknie.finance.spending.log.adapter.persistence

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.SpendingLogTagCustomRepository
import best.jacknie.finance.spending.log.domain.QSpendingLogTagEntity.spendingLogTagEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import org.springframework.stereotype.Repository

@Repository("spendingLogTagRepositoryImpl")
class SpendingLogTagCustomRepositoryImpl: PagingRepositorySupport(SpendingLogTagEntity::class),
  SpendingLogTagCustomRepository {

  override fun findAllPreset(): List<String> {
    return from(spendingLogTagEntity).select(spendingLogTagEntity.tag).groupBy(spendingLogTagEntity.tag).fetch()
  }

  override fun findAllRecommended(summary: String): List<SpendingLogTagEntity> {
    return from(spendingLogTagEntity)
      .join(spendingLogTagEntity.log).fetchJoin()
      .where(spendingLogTagEntity.log.summary.containsIgnoreCase(summary))
      .fetch()
  }
}
