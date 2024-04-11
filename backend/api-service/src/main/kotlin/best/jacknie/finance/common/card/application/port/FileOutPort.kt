package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.FileObject

interface FileOutPort {

  /**
   * 파일 객체 정보 조회
   */
  fun findFileObject(id: Long): FileObject?

  /**
   * 파일 메타데이터 정보 조회
   */
  fun findFileMetadata(fileKey: String): FileMetadataEntity?
}
