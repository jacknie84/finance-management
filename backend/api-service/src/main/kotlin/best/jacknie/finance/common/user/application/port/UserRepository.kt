package best.jacknie.finance.common.user.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

  /**
   * 사용자 이름으로 검색
   */
  fun findByName(name: String): UserEntity?
}
