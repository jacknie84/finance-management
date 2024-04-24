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

  @Transactional(readOnly = true)
  override fun getRecommendedSpendingLogTags(summary: String?): Set<String>? {
    if (summary.isNullOrBlank()) {
      return emptySet()
    }
    val tags = tagRepository.findAllRecommended(summary)
    val exact = tags.filter { it.log.summary == summary }
    if (exact.isNotEmpty()) {
      return exact.map { it.tag }.toSet()
    }
    val like = tags.filter { it.log.summary?.contains(summary, true) == true }
    if (like.isNotEmpty()) {
      return like.map { it.tag }.toSet()
    }
    return emptySet()
  }
}
