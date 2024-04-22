package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.adapter.persistence.jpa.CardRepository
import best.jacknie.finance.common.card.application.port.CardOutPort
import best.jacknie.finance.common.card.application.port.PatchCard
import best.jacknie.finance.common.card.application.port.SaveCard
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.user.domain.UserEntity
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CardPersistenceAdapter(
  private val cardRepository: CardRepository,
  private val entityManager: EntityManager,
): CardOutPort {

  @Transactional(readOnly = true)
  override fun existsByNumber(number: String): Boolean {
    return cardRepository.existsByNumber(number)
  }

  @Transactional
  override fun create(dto: SaveCard, user: UserEntity): CardEntity {
    val entity = CardEntity(
      name = dto.name,
      number = dto.number,
      issuer = dto.issuer,
      user = user,
    )
    return save(entity)
  }

  @Transactional
  override fun update(entity: CardEntity, dto: SaveCard, user: UserEntity): CardEntity {
    entity.apply {
      name = dto.name
      number = dto.number
      issuer = dto.issuer
      this.user = user
    }
    return save(entity)
  }

  @Transactional
  override fun update(entity: CardEntity, dto: PatchCard, user: UserEntity?): CardEntity {
    entity.apply {
      dto.name?.let { name = it }
      dto.number?.let { number = it }
      dto.issuer?.let { issuer = it }
      user?.let { this.user = it }
    }
    return save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(pageable: Pageable): Page<CardEntity> {
    return cardRepository.findAll(pageable)
  }

  @Transactional(readOnly = true)
  override fun findById(id: Long): CardEntity? {
    return cardRepository.findByIdOrNull(id)
  }

  @Transactional(readOnly = true)
  override fun existsById(id: Long): Boolean {
    return cardRepository.existsById(id)
  }

  private fun save(entity: CardEntity): CardEntity {
    try {
      return cardRepository.save(entity)
    } catch (e: Exception) {
      entityManager.clear()
      throw e
    }
  }
}
