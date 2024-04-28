package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.FilePolicy

interface FilePolicyRepository {

  /**
   * 파일 정책 정보 조회
   */
  fun findByName(name: String): FilePolicy?
}
