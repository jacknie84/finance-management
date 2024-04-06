package best.jacknie.finance.spending.log.domain

import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "${TABLE_PREFIX}spending_log_card_usage")
@IdClass(value = SpendingLogCardUsageEntity.Id::class)
data class SpendingLogCardUsageEntity(

  /**
   * 지출 내역 아이디
   */
  @jakarta.persistence.Id
  var logId: Long,

  /**
   * 카드 사용 내역 아이디
   */
  @jakarta.persistence.Id
  var cardUsageId: Long,

  /**
   * 지출 내역
   */
  @ManyToOne
  @JoinColumn(name = "logId", referencedColumnName = "id", insertable = false, updatable = false)
  var log: SpendingLogEntity,

) {

  data class Id(

    /**
     * 지출 내역 아이디
     */
    val logId: Long,

    /**
     * 카드 사용 내역 아이디
     */
    val cardUsageId: Long,
  )
}
