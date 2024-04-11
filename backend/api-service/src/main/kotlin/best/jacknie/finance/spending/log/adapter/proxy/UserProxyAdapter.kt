package best.jacknie.finance.spending.log.adapter.proxy

import best.jacknie.finance.common.user.adapter.export.UserExportedService
import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.spending.log.application.port.UserOutPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserProxyAdapter(
  private val userService: UserExportedService,
): UserOutPort {

  @Transactional
  override fun getOrCreateUser(username: String): UserEntity {
    return userService.getOrCreateUser(username)
  }
}
