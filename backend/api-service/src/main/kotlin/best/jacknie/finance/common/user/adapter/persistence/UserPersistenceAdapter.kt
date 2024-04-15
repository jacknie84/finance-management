package best.jacknie.finance.common.user.adapter.persistence

import best.jacknie.finance.common.user.adapter.persistence.jpa.UserRepository
import best.jacknie.finance.common.user.application.port.UserOutPort
import best.jacknie.finance.common.user.domain.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserPersistenceAdapter(
  private val userRepository: UserRepository
): UserOutPort {

  @Transactional(readOnly = true)
  override fun findByName(username: String): UserEntity? {
    return userRepository.findByName(username)
  }

  @Transactional
  override fun save(entity: UserEntity): UserEntity {
    return userRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(pageable: Pageable): Page<UserEntity> {
    return userRepository.findAll(pageable)
  }

  @Transactional(readOnly = true)
  override fun findById(id: Long): UserEntity? {
    return userRepository.findByIdOrNull(id)
  }
}
