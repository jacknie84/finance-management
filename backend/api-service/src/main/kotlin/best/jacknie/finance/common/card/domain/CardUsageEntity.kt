package best.jacknie.finance.common.card.domain

import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}card_usage")
data class CardUsageEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 승인 번호
   */
  @Column(nullable = false, unique = true)
  var approvalNumber: String,

  /**
   * 가맹점 이름
   */
  @Column(nullable = false)
  var merchant: String,

  /**
   * 이용 내역 상태
   */
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  var status: CardUsageStatus,

  /**
   * 사용 카드
   */
  @ManyToOne
  @JoinColumn(name = "cardId", referencedColumnName = "id")
  var card: CardEntity,

): AuditingEntity()
