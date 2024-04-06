package best.jacknie.finance.common.card.application.service.internal.model

import best.jacknie.finance.common.card.application.port.RawCardUsage
import best.jacknie.finance.common.file.domain.FileObject

interface CardUsageParser {

  /**
   * 카드 사용 내역 조회
   */
  fun getCardUsages(fileObject: FileObject): List<RawCardUsage>
}
