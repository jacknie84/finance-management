package best.jacknie.finance.core.jpa.auditing

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class AuditingEntity {

  /**
   * 생성 시간
   */
  @CreatedDate
  @Column(nullable = false, updatable = false)
  @ColumnDefault("current_timestamp")
  lateinit var createdDate: Instant

  /**
   * 마지막 수정 시간
   */
  @LastModifiedDate
  @Column(nullable = false)
  @ColumnDefault("current_timestamp")
  lateinit var lastModifiedDate: Instant
}
