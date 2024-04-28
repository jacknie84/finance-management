package best.jacknie.finance.spending.log.adapter.client

import best.jacknie.finance.common.user.adapter.export.UserExportedService
import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.spending.log.application.port.UserClient
import org.springframework.stereotype.Component

@Component
class UserClientImpl(
  private val userService: UserExportedService,
): UserClient {

  override fun getOrCreateUser(username: String): UserEntity {
    return userService.getOrCreateUser(username)
  }
}
