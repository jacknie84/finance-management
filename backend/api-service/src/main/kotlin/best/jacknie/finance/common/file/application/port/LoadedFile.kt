package best.jacknie.finance.common.file.application.port

import java.io.InputStream

data class LoadedFile(

  /**
   * 파일 사이즈
   */
  val size: Long,

  /**
   * 파일 내용
   */
  val inputStream: InputStream,
)
