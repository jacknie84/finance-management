package best.jacknie.finance.common.card.adapter.client

import best.jacknie.finance.common.card.application.port.UserClient
import best.jacknie.finance.common.user.adapter.export.UserExportedService
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.stereotype.Component

@Component("card.userClient")
class UserClientImpl(
  private val userExportedService: UserExportedService,
): UserClient {

  override fun getOrCreateUser(username: String): UserEntity {
    return userExportedService.getOrCreateUser(username)
  }
}
