package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.application.port.FilePolicyOutPort
import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.stereotype.Component

@Component
class FilePolicyPersistenceAdapter(
  private val policyRepository: FilePolicyRepository,
): FilePolicyOutPort {

  override fun findOne(name: String): FilePolicy? {
    return policyRepository.findByName(name)
  }
}
