package best.jacknie.finance.common.user.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.BeanNames
import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.MessagingException

@MessagingGateway
interface UserMessagingGateway {

  /**
   * 사용자 조회 또는 생성 처리
   */
  @Throws(MessagingException::class)
  @Gateway(requestChannel = BeanNames.GET_OR_CREATE_USER_REQUEST_CHANNEL)
  fun getOrCreateUser(username: String): UserEntity
}
