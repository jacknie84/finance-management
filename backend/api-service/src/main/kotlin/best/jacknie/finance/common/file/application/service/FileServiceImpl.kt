package best.jacknie.finance.common.file.application.service

import best.jacknie.finance.common.file.application.port.*
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FileServiceImpl(
  private val filePolicyOutPort: FilePolicyOutPort,
  private val fileObjectOutPort: FileObjectOutPort,
  private val fileMetadataOutPort: FileMetadataOutPort,
): FileService {

  @Transactional
  override fun uploadFile(dto: UploadFile): FileMetadataEntity {
    val policy = filePolicyOutPort.findOne(dto.policy) ?: throw HttpStatusCodeException.BadRequest("파일 정책을 찾을 수 없습니다(policy: ${dto.policy})")
    val fileKey = fileObjectOutPort.save(dto.file, policy)
    return fileMetadataOutPort.create(dto, fileKey)
  }

  @Transactional(readOnly = true)
  override fun getFileMetadataPage(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity> {
    return fileMetadataOutPort.findAll(filter, pageable)
  }

  override fun findFileObject(id: Long): FileObject? {
    return fileObjectOutPort.findByMetadataId(id)
  }

  override fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return fileMetadataOutPort.findByKey(fileKey)
  }
}
