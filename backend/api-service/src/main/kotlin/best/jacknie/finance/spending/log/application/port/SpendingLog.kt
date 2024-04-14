package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.spending.log.domain.SpendingLogEntity
import best.jacknie.finance.spending.log.domain.SpendingLogTagEntity
import java.time.Instant

data class SpendingLog(

  /**
   * 지출 내역 아이디
   */
  val id: Long,

  /**
   * 지출 내역
   */
  val summary: String?,

  /**
   * 금액
   */
  val amount: Int,

  /**
   * 태그 목록
   */
  val tags: Set<String>?,

  /**
   * 지출 시간
   */
  val time: Instant,

  /**
   * 사용자
   */
  val user: UserEntity,
) {

  companion object {

    fun from(log: SpendingLogEntity, tags: List<SpendingLogTagEntity>?): SpendingLog {
      return SpendingLog(
        id = log.id!!,
        summary = log.summary,
        amount = log.amount,
        time = log.time.instant,
        tags = tags?.map { it.tag }?.toSet(),
        user = log.user
      )
    }
  }
}
