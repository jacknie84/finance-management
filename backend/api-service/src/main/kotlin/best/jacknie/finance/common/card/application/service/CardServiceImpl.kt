package best.jacknie.finance.common.card.application.service

import best.jacknie.finance.common.card.application.port.*
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardServiceImpl(
    private val cardOutPort: CardOutPort,
    private val userOutPort: UserOutPort,
): CardService {

  @Transactional
  override fun createCard(dto: SaveCard): CardEntity {
    val user = userOutPort.getOrCreateUser(dto.username)
    try {
      return cardOutPort.create(dto, user)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardsPage(pageable: Pageable): Page<CardEntity> {
    return cardOutPort.findAll(pageable)
  }

  @Transactional(readOnly = true)
  override fun getCard(id: Long): CardEntity {
    return cardOutPort.findById(id) ?: notFound(id)
  }

  @Transactional
  override fun putCard(id: Long, dto: SaveCard) {
    val entity = getCard(id)
    val user = userOutPort.getOrCreateUser(dto.username)
    try {
      cardOutPort.update(entity, dto, user)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  @Transactional
  override fun patchCard(id: Long, dto: PatchCard) {
    val entity = getCard(id)
    val user = dto.username?.let { userOutPort.getOrCreateUser(it) }
    try {
      cardOutPort.update(entity, dto, user)
    } catch (e: DataIntegrityViolationException) {
      throw handleDataIntegrityViolationException(e, dto.number)
    }
  }

  private fun notFound(id: Long): Nothing = throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(id: $id)")

  private fun handleDataIntegrityViolationException(e: DataIntegrityViolationException, number: String?): HttpStatusCodeException {
    return if (number != null && cardOutPort.existsByNumber(number)) {
      HttpStatusCodeException.Conflict("already_exists", "이미 등록 된 카드 번호 입니다(${number})")
    } else {
      HttpStatusCodeException.InternalServerError(e.message)
    }
  }
}
