package best.jacknie.finance.common.user.adapter.export

import best.jacknie.finance.common.user.application.port.UserMessagingGateway
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.stereotype.Service

@Service
class UserExportedService(
  private val userMessagingGateway: UserMessagingGateway
) {

  /**
   * 사용자를 조회 하거나 생성 처리
   */
  fun getOrCreateUser(username: String): UserEntity {
    return userMessagingGateway.getOrCreateUser(username)
  }
}
