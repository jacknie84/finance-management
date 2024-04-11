package best.jacknie.finance.common.card.adapter.persistence

import best.jacknie.finance.common.card.adapter.persistence.jpa.CardUsageFileRepository
import best.jacknie.finance.common.card.application.port.CardUsageFileOutPort
import best.jacknie.finance.common.card.application.port.SaveCardUsageFile
import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CardUsageFilePersistenceAdapter(
  private val fileRepository: CardUsageFileRepository
): CardUsageFileOutPort {

  @Transactional
  override fun create(dto: SaveCardUsageFile, card: CardEntity): CardUsageFileEntity {
    val entity = CardUsageFileEntity(
      description = dto.description,
      fileKey = dto.fileKey,
      card = card,
    )
    return fileRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity> {
    return fileRepository.findAll(cardId, pageable)
  }

  @Transactional(readOnly = true)
  override fun findOne(cardId: Long, id: Long): CardUsageFileEntity? {
    return fileRepository.findByCardIdAndId(cardId, id)
  }
}
