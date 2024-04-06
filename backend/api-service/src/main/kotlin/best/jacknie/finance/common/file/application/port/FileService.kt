package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.FileMetadataEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FileService {

  /**
   * 파일 업로드 처리
   */
  fun uploadFile(dto: UploadFile): FileMetadataEntity

  /**
   * 파일 메타데이터 목록 페이지 조회
   */
  fun getFileMetadataPage(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity>
}
