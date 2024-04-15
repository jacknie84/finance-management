package best.jacknie.finance.common.card.adapter.message

import best.jacknie.finance.common.card.application.port.CardMessagingGateway.Companion.HEADER_NAME_ID
import best.jacknie.finance.common.card.application.port.CardService
import best.jacknie.finance.common.card.application.port.PatchCard
import best.jacknie.finance.common.card.application.port.SaveCard
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.core.BeanNames
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class CardServiceActivators(
  private val cardService: CardService,
) {

  /**
   * 카드 저장 처리
   */
  @ServiceActivator(inputChannel = BeanNames.SAVE_CARD_REQUEST_CHANNEL)
  fun saveCard(@Header(name = HEADER_NAME_ID, required = false) id: Long?, @Payload card: Any): CardEntity {
    return when (card) {
      is SaveCard -> if (id == null) cardService.createCard(card) else cardService.putCard(id, card)
      is PatchCard -> cardService.patchCard(id!!, card)
      else -> error("unsupported save card type: ${card::class}")
    }
  }
}
