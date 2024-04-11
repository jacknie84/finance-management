package best.jacknie.finance.common.file.adapter.persistence.fs

import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.web.multipart.MultipartFile

interface FileObjectRepository {

  /**
   * 파일 객체 저장 처리
   */
  fun save(file: MultipartFile, policy: FilePolicy): String

  /**
   * 파일 로드
   */
  fun load(key: String): LoadedFile
}
