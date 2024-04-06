package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.application.port.FileObjectOutPort
import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileObjectPersistenceAdapter(
  private val objectRepository: FileObjectRepository,
): FileObjectOutPort {

  override fun save(file: MultipartFile, policy: FilePolicy): String {
    return objectRepository.save(file, policy)
  }
}
