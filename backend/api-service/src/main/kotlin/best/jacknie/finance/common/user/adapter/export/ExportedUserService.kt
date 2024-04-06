package best.jacknie.finance.common.user.adapter.export

import best.jacknie.finance.common.user.application.port.UserService
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.stereotype.Service

@Service
class ExportedUserService(
  private val userService: UserService
) {

  fun getOrCreateUser(username: String): UserEntity {
    return userService.getOrCreateUser(username)
  }
}
