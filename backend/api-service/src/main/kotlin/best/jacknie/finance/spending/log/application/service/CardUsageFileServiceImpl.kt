package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.common.card.application.service.internal.model.CardUsageParser
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import best.jacknie.finance.spending.log.application.port.*
import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardUsageFileServiceImpl(
  private val cardClient: CardClient,
  private val fileRepository: CardUsageFileRepository,
  private val fileClient: FileClient,
  private val usageParser: CardUsageParser,
): CardUsageFileService {

  @Transactional
  override fun createCardUsageFile(cardId: Long, dto: SaveCardUsageFile): CardUsageFileEntity {
    val card = cardClient.getCard(cardId)
    if (dto.policy != "card-usage") {
      throw HttpStatusCodeException.BadRequest("지원 하지 않는 파일 정책 이름 입니다(${dto.policy})")
    }
    val usage = CardUsageFileEntity(
      description = dto.description,
      fileKey = dto.fileKey,
      card = card,
    )
    return fileRepository.save(usage)
  }

  @Transactional(readOnly = true)
  override fun getCardUsageFilesPage(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity> {
    cardClient.getCard(cardId)
    return fileRepository.findAll(cardId, pageable)
  }

  @Transactional(readOnly = true)
  override fun getCardUsageFileObject(cardId: Long, id: Long): FileObject {
    val usageFile =  fileRepository.findByCardIdAndId(cardId, id) ?: notFound(cardId, id)
    val metadata = fileClient.findFileMetadata(usageFile.fileKey) ?: conflict(usageFile.fileKey)
    return fileClient.findFileObject(metadata.id!!) ?: conflict(metadata.id!!)
  }

  @Transactional(readOnly = true)
  override fun getCardUsages(cardId: Long, id: Long): List<RawCardUsage> {
    val fileObject = getCardUsageFileObject(cardId, id)
    return usageParser.getCardUsages(fileObject)
  }

  private fun notFound(cardId: Long, id: Long): Nothing = throw throw HttpStatusCodeException.NotFound("카드 사용 내역 파일을 찾을 수 없습니다(cardId: $cardId, id: $id)")
  private fun conflict(fileKey: String): Nothing = throw HttpStatusCodeException.Conflict("파일 정보를 찾을 수 없습니다(fileKey: ${fileKey})")
  private fun conflict(metadataId: Long): Nothing = throw HttpStatusCodeException.NotFound("파일 정보를 찾을 수 없습니다(metadataId: ${metadataId})")
}
