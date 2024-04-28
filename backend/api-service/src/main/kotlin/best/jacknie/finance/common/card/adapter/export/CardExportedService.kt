package best.jacknie.finance.common.card.adapter.export

import best.jacknie.finance.common.card.application.port.CardService
import best.jacknie.finance.common.card.domain.CardEntity
import org.springframework.stereotype.Service

@Service
class CardExportedService(
  private val cardService: CardService,
) {

  fun getCard(id: Long): CardEntity {
    return cardService.getCard(id)
  }
}
