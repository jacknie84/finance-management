package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.core.BeanNames
import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.MessagingException
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

@MessagingGateway
interface CardMessagingGateway {

  /**
   * 카드 생성 처리
   */
  @Throws(MessagingException::class)
  @Gateway(requestChannel = BeanNames.SAVE_CARD_REQUEST_CHANNEL)
  fun createCard(card: SaveCard): CardEntity

  /**
   * 카드 수정 처리
   */
  @Throws(MessagingException::class)
  @Gateway(requestChannel = BeanNames.SAVE_CARD_REQUEST_CHANNEL)
  fun putCard(@Header(name = HEADER_NAME_ID) id: Long, @Payload card: SaveCard): CardEntity

  /**
   * 카드 수정 처리
   */
  @Throws(MessagingException::class)
  @Gateway(requestChannel = BeanNames.SAVE_CARD_REQUEST_CHANNEL)
  fun patchCard(@Header(name = HEADER_NAME_ID) id: Long, @Payload card: PatchCard): CardEntity

  companion object {

    /**
     * 카드 아이디 헤더 이름
     */
    const val HEADER_NAME_ID = "CardMessagingGateway_id"
  }
}
