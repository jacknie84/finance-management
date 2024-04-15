package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.core.BeanNames
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.MessagingException
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

@MessagingGateway(defaultRequestChannel = BeanNames.SAVE_CARD_USAGE_REQUEST_CHANNEL)
interface CardUsageMessagingGateway {

  /**
   * 카드 사용 내역 저장 처리
   */
  @Throws(MessagingException::class)
  fun createCardUsage(@Header(name = HEADER_NAME_CARD_ID) cardId: Long, @Payload payload: SaveCardUsage): CardUsage

  /**
   * 카드 사용 내역 수정 처리
   */
  @Throws(MessagingException::class)
  fun putCardUsage(@Header(name = HEADER_NAME_CARD_ID) cardId: Long, @Header(name = HEADER_NAME_ID) id: Long, dto: SaveCardUsage): CardUsage

  companion object {
    /**
     * 카드 사용 내역 아이디 헤더 이름
     */
    const val HEADER_NAME_ID = "CardUsageMessagingGateway_id"

    /**
     * 카드 아이디 헤더 이름
     */
    const val HEADER_NAME_CARD_ID = "CardUsageMessagingGateway_cardId"
  }
}
