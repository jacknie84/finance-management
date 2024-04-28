package best.jacknie.finance.common.file.domain

import org.springframework.http.MediaType
import org.springframework.util.unit.DataSize

data class FilePolicy(

  /**
   * 파일 정책 이름
   */
  val name: String,

  /**
   * 파일 사이즈 제한(bytes)
   */
  val sizeLimit: DataSize?,

  /**
   * 허용 하는 파일 타입 목록
   */
  val allowContentTypes: Set<MediaType>?,

  /**
   * 허용 하는 파일 확장자 이름 목록
   */
  val allowFileExtensions: Set<String>?,
)
