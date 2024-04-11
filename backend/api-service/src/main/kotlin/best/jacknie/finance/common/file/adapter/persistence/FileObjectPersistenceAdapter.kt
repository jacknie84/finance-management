package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.adapter.persistence.fs.FileObjectRepository
import best.jacknie.finance.common.file.adapter.persistence.jpa.FileMetadataRepository
import best.jacknie.finance.common.file.application.port.FileObjectOutPort
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileObjectPersistenceAdapter(
  private val objectRepository: FileObjectRepository,
  private val metadataRepository: FileMetadataRepository,
): FileObjectOutPort {

  override fun save(file: MultipartFile, policy: FilePolicy): String {
    return objectRepository.save(file, policy)
  }

  override fun findByMetadataId(metadataId: Long): FileObject? {
    return metadataRepository.findByIdOrNull(metadataId)
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
}
