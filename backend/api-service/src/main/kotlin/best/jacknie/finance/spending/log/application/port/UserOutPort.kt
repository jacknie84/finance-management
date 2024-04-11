package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.common.user.domain.UserEntity

interface UserOutPort {

  /**
   * 사용자를 조회 하거나 생성 처리
   */
  fun getOrCreateUser(username: String): UserEntity
}
