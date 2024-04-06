package best.jacknie.finance.common.user.domain

import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}user")
data class UserEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 사용자 이름
   */
  @Column(nullable = false, unique = true)
  var name: String,

  /**
   * 사용자 출력 이름
   */
  var displayName: String? = null,


): AuditingEntity()
