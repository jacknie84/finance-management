package best.jacknie.finance.common.user.application.service

import best.jacknie.finance.common.user.application.port.PatchUser
import best.jacknie.finance.common.user.application.port.UserOutPort
import best.jacknie.finance.common.user.application.port.UserService
import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
  private val userOutPort: UserOutPort
): UserService {

  @Transactional
  override fun getOrCreateUser(username: String): UserEntity {
    return userOutPort.findByName(username) ?: userOutPort.save(UserEntity(name = username))
  }

  @Transactional(readOnly = true)
  override fun getUsersPage(pageable: Pageable): Page<UserEntity> {
    return userOutPort.findAll(pageable)
  }

  @Transactional
  override fun patchUser(id: Long, dto: PatchUser) {
    val entity = userOutPort.findById(id) ?: throw HttpStatusCodeException.NotFound("사용자 정보를 찾을 수 없습니다(id: $id)")
    dto.displayName?.let { entity.displayName = it }
    userOutPort.save(entity)
  }
}
