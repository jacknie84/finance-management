package best.jacknie.finance.common.user.adapter.export

import best.jacknie.finance.common.user.application.port.UserService
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.stereotype.Service

@Service
class UserExportedService(
  private val userService: UserService
) {

  /**
   * 사용자를 조회 하거나 생성 처리
   */
  fun getOrCreateUser(username: String): UserEntity {
    return userService.getOrCreateUser(username)
  }
}
