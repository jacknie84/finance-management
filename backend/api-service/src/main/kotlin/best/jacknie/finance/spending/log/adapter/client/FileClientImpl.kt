package best.jacknie.finance.spending.log.adapter.client

import best.jacknie.finance.common.file.adapter.export.FileExportedService
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.spending.log.application.port.FileClient
import org.springframework.stereotype.Component

@Component
class FileClientImpl(
  private val fileExportedService: FileExportedService,
): FileClient {

  override fun findFileObject(id: Long): FileObject? {
    return fileExportedService.findFileObject(id)
  }

  override fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return fileExportedService.findFileMetadata(fileKey)
  }
}
