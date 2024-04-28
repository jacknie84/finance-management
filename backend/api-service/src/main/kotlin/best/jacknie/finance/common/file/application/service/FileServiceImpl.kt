package best.jacknie.finance.common.file.application.service

import best.jacknie.finance.common.file.application.port.*
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FileServiceImpl(
  private val policyRepository: FilePolicyRepository,
  private val objectRepository: FileObjectRepository,
  private val metadataRepository: FileMetadataRepository,
): FileService {

  @Transactional
  override fun uploadFile(dto: UploadFile): FileMetadataEntity {
    val policy = policyRepository.findByName(dto.policy)
      ?: throw HttpStatusCodeException.BadRequest("파일 정책을 찾을 수 없습니다(policy: ${dto.policy})")
    val fileKey = objectRepository.save(dto.file, policy)
    val metadata = FileMetadataEntity(
      key = fileKey,
      name = dto.file.originalFilename ?: dto.file.name,
      size = dto.file.size,
      contentType = dto.file.contentType ?: MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    return metadataRepository.save(metadata)
  }

  @Transactional(readOnly = true)
  override fun getFileMetadataPage(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity> {
    return metadataRepository.findAll(filter, pageable)
  }

  override fun findFileObject(id: Long): FileObject? {
    return metadataRepository.findByIdOrNull(id)
      ?.let { it to objectRepository.load(it.key) }
      ?.let {
        FileObject(
          key = it.first.key,
          contentType = it.first.contentType,
          filename = it.first.name,
          fileSize = it.second.size,
          lastModifiedDate = it.first.lastModifiedDate,
          content = it.second.inputStream,
        )
      }
  }

  override fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return metadataRepository.findByKey(fileKey)
  }
}
