package best.jacknie.finance.spending.log.domain

import best.jacknie.finance.common.card.domain.CardEntity
import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}card_usage_file")
data class CardUsageFileEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 카드 사용 내역 파일 설명
   */
  var description: String? = null,

  /**
   * 업로드 파일키
   */
  var fileKey: String,

  /**
   * 카드 정보
   */
  @ManyToOne
  @JoinColumn(name = "cardId", referencedColumnName = "id")
  var card: CardEntity,

): AuditingEntity()
