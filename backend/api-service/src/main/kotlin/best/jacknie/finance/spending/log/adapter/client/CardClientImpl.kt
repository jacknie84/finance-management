package best.jacknie.finance.spending.log.adapter.client

import best.jacknie.finance.common.card.adapter.export.CardExportedService
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.spending.log.application.port.CardClient
import org.springframework.stereotype.Component

@Component
class CardClientImpl(
  private val cardExportedService: CardExportedService,
): CardClient {

  override fun getCard(cardId: Long): CardEntity {
    return cardExportedService.getCard(cardId)
  }
}
