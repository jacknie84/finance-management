package best.jacknie.finance.common.file.application.port

import org.springframework.web.multipart.MultipartFile

data class UploadFile(

  /**
   * 업로드 정책
   */
  var policy: String,

  /**
   * 업로드 파일
   */
  var file: MultipartFile,
)
