package best.jacknie.finance.common.user.application.service

import best.jacknie.finance.common.user.application.port.PatchUser
import best.jacknie.finance.common.user.application.port.UserRepository
import best.jacknie.finance.common.user.application.port.UserService
import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
  private val userRepository: UserRepository,
): UserService {

  @Transactional
  override fun getOrCreateUser(username: String): UserEntity {
    return userRepository.findByName(username) ?: userRepository.save(UserEntity(name = username))
  }

  @Transactional(readOnly = true)
  override fun getUsersPage(pageable: Pageable): Page<UserEntity> {
    return userRepository.findAll(pageable)
  }

  @Transactional
  override fun patchUser(id: Long, dto: PatchUser) {
    val user = userRepository.findByIdOrNull(id) ?: throw HttpStatusCodeException.NotFound("사용자 정보를 찾을 수 없습니다(id: $id)")
    dto.displayName?.let { user.displayName = it }
    userRepository.save(user)
  }
}
