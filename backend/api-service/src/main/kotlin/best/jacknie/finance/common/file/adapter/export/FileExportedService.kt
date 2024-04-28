package best.jacknie.finance.common.file.adapter.export

import best.jacknie.finance.common.file.application.port.FileService
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import org.springframework.stereotype.Service

@Service
class FileExportedService(
  private val fileService: FileService,
) {

  /**
   * 파일 객체 정보 조회
   */
  fun findFileObject(id: Long): FileObject? {
    return fileService.findFileObject(id)
  }

  /**
   * 파일 메타데이터 정보 조회
   */
  fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return fileService.findFileMetadata(fileKey)
  }
}
