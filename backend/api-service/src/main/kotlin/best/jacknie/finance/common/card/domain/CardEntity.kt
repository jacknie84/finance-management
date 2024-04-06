package best.jacknie.finance.common.card.domain

import best.jacknie.finance.common.user.domain.UserEntity
import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}card")
data class CardEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 카드 이름
   */
  @Column(nullable = false)
  var name: String,

  /**
   * 카드 번호
   */
  @Column(nullable = false, unique = true)
  var number: String,

  /**
   * 카드 발행사
   */
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  var issuer: CardIssuer,

  /**
   * 카드 사용자
   */
  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id")
  var user: UserEntity,

): AuditingEntity()
