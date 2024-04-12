package best.jacknie.finance.spending.log.adapter.proxy

import best.jacknie.finance.common.card.adapter.export.CardExportedService
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.spending.log.application.port.CardOutPort
import org.springframework.stereotype.Component

@Component
class CardProxyAdapter(
  private val cardExportedService: CardExportedService,
): CardOutPort {

  override fun getCard(cardId: Long): CardEntity {
    return cardExportedService.getCard(cardId)
  }
}
