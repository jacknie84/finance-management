package best.jacknie.finance.common.file.domain

import best.jacknie.finance.core.jpa.auditing.AuditingEntity
import best.jacknie.finance.core.jpa.metadata.TABLE_PREFIX
import jakarta.persistence.*

@Entity
@Table(name = "${TABLE_PREFIX}file")
data class FileMetadataEntity(

  /**
   * 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  /**
   * 파일 키
   */
  @Column(name = "fileKey", nullable = false, unique = true)
  var key: String,

  /**
   * 파일 이름
   */
  @Column(name = "filename", nullable = false)
  var name: String,

  /**
   * 파일 사이즈
   */
  @Column(name = "fileSize", nullable = false)
  var size: Long,

  /**
   * 파일 타임
   */
  @Column(nullable = false)
  var contentType: String,

): AuditingEntity()
