package best.jacknie.finance.common.file.application.port

data class FileMetadataFilter(

  /**
   * 파일 키 목록
   */
  var key: Set<String>? = null,

)
