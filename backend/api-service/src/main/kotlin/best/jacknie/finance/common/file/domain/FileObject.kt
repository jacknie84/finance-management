package best.jacknie.finance.common.file.domain

import java.io.InputStream
import java.time.Instant

data class FileObject(

  /**
   * 파일 키
   */
  val key: String,

  /**
   * 파일 타입
   */
  val contentType: String,

  /**
   * 파일 이름
   */
  val filename: String,

  /**
   * 파일 사이즈
   */
  val fileSize: Long,

  /**
   * 마지막 수정 시간
   */
  val lastModifiedDate: Instant,

  /**
   * 파일 내용
   */
  val content: InputStream,

)
