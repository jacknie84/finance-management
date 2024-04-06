package best.jacknie.finance.common.file.adapter.export

import best.jacknie.finance.common.file.adapter.persistence.FileMetadataRepository
import best.jacknie.finance.common.file.adapter.persistence.FileObjectRepository
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ExportedFileService(
  private val metadataRepository: FileMetadataRepository,
  private val objectRepository: FileObjectRepository,
) {

  /**
   * 파일 객체 정보 조회
   */
  fun findFileObject(id: Long): FileObject? {
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

  /**
   * 파일 메타데이터 정보 조회
   */
  fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return metadataRepository.findByKey(fileKey)
  }
}
