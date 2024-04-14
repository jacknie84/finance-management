package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.spending.log.domain.CardUsageEntity
import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import best.jacknie.finance.spending.log.domain.CardUsageStatus
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity

data class CardUsage(

  /**
   * 아이디
   */
  val id: Long,

  /**
   * 승인 번호
   */
  val approvalNumber: String,

  /**
   * 가맹점 이름
   */
  val merchant: String,

  /**
   * 이용 내역 상태
   */
  val status: CardUsageStatus,

  /**
   * 카드 내역 파일
   */
  val file: CardUsageFileEntity,

  /**
   * 지출 내역
   */
  val log: SpendingLog,

) {

  companion object {

    fun from(usage: CardUsageEntity, tags: List<SpendingLogTagEntity>?): CardUsage {
      return CardUsage(
        id = usage.id!!,
        approvalNumber = usage.approvalNumber,
        merchant = usage.merchant,
        status = usage.status,
        file = usage.file,
        log = SpendingLog.from(usage.log, tags)
      )
    }
  }
}
