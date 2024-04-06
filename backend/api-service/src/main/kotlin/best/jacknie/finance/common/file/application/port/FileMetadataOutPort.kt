package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.FileMetadataEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FileMetadataOutPort {

  /**
   * 파일 메타데이터 저장 처리
   */
  fun create(dto: UploadFile, fileKey: String): FileMetadataEntity

  /**
   * 파일 메타데이터 조회
   */
  fun findAll(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity>
}
