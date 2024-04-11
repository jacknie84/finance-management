package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import best.jacknie.finance.spending.log.application.port.SpendingLogsFilter
import best.jacknie.finance.spending.log.domain.QSpendingLogEntity.spendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Suppress("unused")
class SpendingLogCustomRepositoryImpl: PagingRepositorySupport(SpendingLogEntity::class),
    SpendingLogCustomRepository {

  override fun findAll(filter: SpendingLogsFilter, pageable: Pageable): Page<SpendingLogEntity> {
    return getPage(spendingLogEntity, filter, pageable)
  }
}
