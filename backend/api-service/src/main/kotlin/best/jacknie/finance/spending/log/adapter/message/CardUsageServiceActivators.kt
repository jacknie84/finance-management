package best.jacknie.finance.spending.log.adapter.message

import best.jacknie.finance.core.BeanNames
import best.jacknie.finance.spending.log.application.port.CardUsage
import best.jacknie.finance.spending.log.application.port.CardUsageMessagingGateway
import best.jacknie.finance.spending.log.application.port.CardUsageService
import best.jacknie.finance.spending.log.application.port.SaveCardUsage
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class CardUsageServiceActivators(
  private val usageService: CardUsageService,
) {

  @ServiceActivator(inputChannel = BeanNames.SAVE_CARD_USAGE_REQUEST_CHANNEL)
  fun saveCardUsage(
    @Header(name = CardUsageMessagingGateway.HEADER_NAME_ID, required = false) id: Long?,
    @Header(name = CardUsageMessagingGateway.HEADER_NAME_CARD_ID) cardId: Long,
    @Payload payload: SaveCardUsage,
  ): CardUsage {
    return if (id == null) {
      usageService.createCardUsage(cardId, payload)
    } else {
      usageService.putCardUsage(cardId, id, payload)
    }
  }
}
