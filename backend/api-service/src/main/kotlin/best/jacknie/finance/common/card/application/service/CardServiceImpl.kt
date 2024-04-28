package best.jacknie.finance.common.card.application.service

import best.jacknie.finance.common.card.application.port.*
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.core.jpa.utils.isDuplicateKeyError
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardServiceImpl(
  private val cardRepository: CardRepository,
  private val userClient: UserClient,
): CardService {

  override fun createCard(dto: SaveCard): CardEntity {
    val user = userClient.getOrCreateUser(dto.username)
    try {
      return cardRepository.save(CardEntity(
        name = dto.name,
        number = dto.number,
        issuer = dto.issuer,
        user = user,
      ))
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardsPage(pageable: Pageable): Page<CardEntity> {
    return cardRepository.findAll(pageable)
  }

  @Transactional(readOnly = true)
  override fun getCard(id: Long): CardEntity {
    return cardRepository.findByIdOrNull(id) ?: notFound(id)
  }

  override fun putCard(id: Long, dto: SaveCard): CardEntity {
    val card = getCard(id)
    val user = userClient.getOrCreateUser(dto.username)
    try {
      card.apply {
        name = dto.name
        number = dto.number
        issuer = dto.issuer
        this.user = user
      }
      return cardRepository.save(card)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  override fun patchCard(id: Long, dto: PatchCard): CardEntity {
    val card = getCard(id)
    val user = dto.username?.let { userClient.getOrCreateUser(it) }
    try {
      card.apply {
        dto.name?.let { name = it }
        dto.number?.let { number = it }
        dto.issuer?.let { issuer = it }
        user?.let { this.user = it }
      }
      return cardRepository.save(card)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  private fun notFound(id: Long): Nothing = throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(id: $id)")

  private fun handleDataIntegrityViolationException(e: DataIntegrityViolationException, number: String?): HttpStatusCodeException {
    return if (number != null && isDuplicateKeyError(e)) {
      HttpStatusCodeException.Conflict("already_exists", "이미 등록 된 카드 번호 입니다(${number})")
    } else {
      HttpStatusCodeException.InternalServerError(e.message)
    }
  }
}
