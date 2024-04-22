package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.spending.log.application.port.SpendingLogTagRepository
import best.jacknie.finance.spending.log.application.port.SpendingLogTagService
import best.jacknie.finance.spending.log.application.port.SpendingLogTagsPreset
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpendingLogTagServiceImpl(
  private val tagRepository: SpendingLogTagRepository,
): SpendingLogTagService {

  @Transactional(readOnly = true)
  override fun getSpendingLogTagsPreset(): SpendingLogTagsPreset {
    val tags = tagRepository.findAllPreset()
    return SpendingLogTagsPreset(tags)
  }
}
