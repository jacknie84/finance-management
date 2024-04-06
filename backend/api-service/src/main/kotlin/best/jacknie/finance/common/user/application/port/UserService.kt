package best.jacknie.finance.common.user.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {

  /**
   * 사용자를 조회 하거나 생성 처리
   */
  fun getOrCreateUser(username: String): UserEntity

  /**
   * 사용자 목록 페이지 조회
   */
  fun getUsersPage(pageable: Pageable): Page<UserEntity>

  /**
   * 사용자 정보 수정 처리
   */
  fun patchUser(id: Long, dto: PatchUser)
}
