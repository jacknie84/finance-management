package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.application.port.FileMetadataFilter
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FileMetadataCustomRepository {

  /**
   * 파일 메타 데이터 목록 페이지 조회
   */
  fun findAll(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity>
}
