package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.user.domain.UserEntity

interface UserClient {

  /**
   * 사용자가 있으면 조회 하고 없으면 생성 처리
   */
  fun getOrCreateUser(username: String): UserEntity
}
