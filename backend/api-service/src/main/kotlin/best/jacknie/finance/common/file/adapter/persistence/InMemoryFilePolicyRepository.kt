package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.application.port.FilePolicyRepository
import best.jacknie.finance.common.file.domain.FilePolicy
import best.jacknie.finance.core.file.config.FileProperties
import org.springframework.stereotype.Repository

@Repository
class InMemoryFilePolicyRepository(
  private val fileProperties: FileProperties,
): FilePolicyRepository {

  override fun findByName(name: String): FilePolicy? {
    return fileProperties.policies[name]
      ?.let {
        FilePolicy(
          name = name,
          sizeLimit = it.sizeLimit,
          allowContentTypes = it.allowContentTypes,
          allowFileExtensions = it.allowFileExtensions,
        )
      }
  }
}
