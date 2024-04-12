package best.jacknie.finance.common.card.adapter.proxy

import best.jacknie.finance.common.card.application.port.UserOutPort
import best.jacknie.finance.common.user.adapter.export.UserExportedService
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.stereotype.Component

@Component("spendingLogUserProxyAdapter")
class UserProxyAdapter(
  private val userExportedService: UserExportedService,
): UserOutPort {

  override fun getOrCreateUser(username: String): UserEntity {
    return userExportedService.getOrCreateUser(username)
  }
}
