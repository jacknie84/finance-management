package best.jacknie.finance.common.user.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserOutPort {

  /**
   * 사용자 정보 조회
   */
  fun findByName(username: String): UserEntity?

  /**
   * 사용자 정보 저장 처리
   */
  fun save(entity: UserEntity): UserEntity

  /**
   * 사용자 목록 페이지 조회
   */
  fun findAll(pageable: Pageable): Page<UserEntity>

  /**
   * 사용자 정보 조회
   */
  fun findById(id: Long): UserEntity?
}
