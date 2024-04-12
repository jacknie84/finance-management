package best.jacknie.finance.spending.log.domain

import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}spending_log_tag")
@IdClass(value = SpendingLogTagEntity.PrimaryKey::class)
data class SpendingLogTagEntity(

  /**
   * 지출 내역 아이디
   */
  @Id
  var logId: Long,

  /**
   * 태그 이름
   */
  @Id
  var tag: String,

  /**
   * 지출 내역
   */
  @ManyToOne(optional = false)
  @JoinColumn(name = "logId", referencedColumnName = "id", insertable = false, updatable = false)
  var log: SpendingLogEntity,

) {

  data class PrimaryKey(

    /**
     * 지출 내역 아이디
     */
    var logId: Long? = null,

    /**
     * 태그 이름
     */
    var tag: String? = null,

  )
}
