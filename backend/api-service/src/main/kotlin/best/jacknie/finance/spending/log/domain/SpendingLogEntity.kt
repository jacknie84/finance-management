package best.jacknie.finance.spending.log.domain

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}spending_log")
data class SpendingLogEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 지출 내용
   */
  var summary: String? = null,

  /**
   * 지출 금액
   */
  @Column(nullable = false)
  var amount: Int,

  /**
   * 지출 시간
   */
  @Embedded
  @AttributeOverrides(value = [
    AttributeOverride(name = "instant", column = Column(name = "time", nullable = false)),
    AttributeOverride(name = "zone", column = Column(name = "timeZone", nullable = false)),
    AttributeOverride(name = "year", column = Column(name = "timeYear", nullable = false)),
    AttributeOverride(name = "month", column = Column(name = "timeMonth", nullable = false)),
    AttributeOverride(name = "dayOfMonth", column = Column(name = "timeDayOfMonth", nullable = false)),
    AttributeOverride(name = "dayOfWeek", column = Column(name = "timeDayOfWeek", nullable = false)),
    AttributeOverride(name = "hour", column = Column(name = "timeHour", nullable = false)),
  ])
  var time: SpendingTime,

  /**
   * 지출 사용자 정보
   */
  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id")
  var user: UserEntity,

): AuditingEntity()
