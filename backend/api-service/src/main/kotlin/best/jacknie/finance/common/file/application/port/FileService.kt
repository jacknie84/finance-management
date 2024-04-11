package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject
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

  /**
   * 파일 객체 정보 조회
   */
  fun findFileObject(id: Long): FileObject?

  /**
   * 파일 메타데이터 정보 조회
   */
  fun findFileMetadata(fileKey: String): FileMetadataEntity?
}
