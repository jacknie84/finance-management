package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.web.multipart.MultipartFile

interface FileObjectOutPort {

  /**
   * 파일 객체 저장 처리
   */
  fun save(file: MultipartFile, policy: FilePolicy): String
}
