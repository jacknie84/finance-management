package best.jacknie.finance.spending.log.adapter.proxy

import best.jacknie.finance.spending.log.application.port.FileOutPort
import best.jacknie.finance.common.file.adapter.export.FileExportedService
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
import org.springframework.stereotype.Component

@Component
class FileProxyAdapter(
  private val fileExportedService: FileExportedService,
): FileOutPort {

  override fun findFileObject(id: Long): FileObject? {
    return fileExportedService.findFileObject(id)
  }

  override fun findFileMetadata(fileKey: String): FileMetadataEntity? {
    return fileExportedService.findFileMetadata(fileKey)
  }
}
