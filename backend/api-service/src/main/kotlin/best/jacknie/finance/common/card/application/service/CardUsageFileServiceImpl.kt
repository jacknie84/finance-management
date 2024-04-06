package best.jacknie.finance.common.card.application.service

import best.jacknie.finance.common.card.application.port.*
import best.jacknie.finance.common.card.application.service.internal.model.CardUsageParser
import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import best.jacknie.finance.common.file.adapter.export.ExportedFileService
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardUsageFileServiceImpl(
  private val cardOutPort: CardOutPort,
  private val fileOutPort: CardUsageFileOutPort,
  private val fileService: ExportedFileService,
  private val usageParser: CardUsageParser,
): CardUsageFileService {

  @Transactional
  override fun createCardUsageFile(cardId: Long, dto: SaveCardUsageFile): CardUsageFileEntity {
    val card = cardOutPort.findById(cardId) ?: throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(cardId: $cardId)")
    if (dto.policy != "card-usage") {
      throw HttpStatusCodeException.BadRequest("지원 하지 않는 파일 정책 이름 입니다(${dto.policy})")
    } else {
      return fileOutPort.create(dto, card)
    }
  }

  @Transactional(readOnly = true)
  override fun getCardUsageFilesPage(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity> {
    if (!cardOutPort.existsById(cardId)) {
      throw HttpStatusCodeException.NotFound("카드 정보를 찾을 수 없습니다(cardId: $cardId)")
    }
    return fileOutPort.findAll(cardId, pageable)
  }

  @Transactional(readOnly = true)
  override fun getCardUsageFileObject(cardId: Long, id: Long): FileObject {
    val entity = fileOutPort.findOne(cardId, id) ?: throw HttpStatusCodeException.NotFound("카드 사용 내역 파일을 찾을 수 없습니다(cardId: $cardId, id: $id)")
    val metadata = fileService.findFileMetadata(entity.fileKey) ?: throw HttpStatusCodeException.Conflict("파일 정보를 찾을 수 없습니다(fileKey: ${entity.fileKey})")
    return fileService.findFileObject(metadata.id!!) ?: throw HttpStatusCodeException.NotFound("파일 정보를 찾을 수 없습니다(fileId: ${metadata.id})")
  }

  @Transactional(readOnly = true)
  override fun getCardUsages(cardId: Long, id: Long): List<RawCardUsage> {
    val fileObject = getCardUsageFileObject(cardId, id)
    return usageParser.getCardUsages(fileObject)
  }
}
