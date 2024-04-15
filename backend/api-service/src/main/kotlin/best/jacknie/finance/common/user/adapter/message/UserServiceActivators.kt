package best.jacknie.finance.common.user.adapter.message

import best.jacknie.finance.common.user.application.port.UserService
import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.BeanNames
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UserServiceActivators(
  private val userService: UserService,
) {

  @ServiceActivator(inputChannel = BeanNames.GET_OR_CREATE_USER_REQUEST_CHANNEL)
  fun getOrCreateUser(@Payload username: String): UserEntity {
    return userService.getOrCreateUser(username)
  }

}
