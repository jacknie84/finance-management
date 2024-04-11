package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.adapter.persistence.jpa.FileMetadataRepository
import best.jacknie.finance.common.file.application.port.FileMetadataFilter
import best.jacknie.finance.common.file.application.port.FileMetadataOutPort
import best.jacknie.finance.common.file.application.port.UploadFile
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FileMetadataPersistenceAdapter(
    private val metadataRepository: FileMetadataRepository,
): FileMetadataOutPort {

  @Transactional
  override fun create(dto: UploadFile, fileKey: String): FileMetadataEntity {
    val entity = FileMetadataEntity(
      key = fileKey,
      name = dto.file.originalFilename ?: dto.file.name,
      size = dto.file.size,
      contentType = dto.file.contentType ?: MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    return metadataRepository.save(entity)
  }

  @Transactional(readOnly = true)
  override fun findAll(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity> {
    return metadataRepository.findAll(filter, pageable)
  }

  @Transactional(readOnly = true)
  override fun findById(id: Long): FileMetadataEntity? {
    return metadataRepository.findByIdOrNull(id)
  }

  @Transactional(readOnly = true)
  override fun findByKey(fileKey: String): FileMetadataEntity? {
    return metadataRepository.findByKey(fileKey)
  }
}
