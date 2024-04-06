package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.domain.FileMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileMetadataRepository: JpaRepository<FileMetadataEntity, Long>, FileMetadataCustomRepository {

  /**
   * 파일 메타데이터 조회
   */
  fun findByKey(key: String): FileMetadataEntity?
}
