package best.jacknie.finance.core.file.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.MediaType
import org.springframework.util.unit.DataSize

@ConfigurationProperties(prefix = "app.file")
data class FileProperties(

  /**
   * 파일 정책 목록
   */
  var policies: MutableMap<String, FilePolicyProperties>

) {

  data class FilePolicyProperties(

    /**
     * 파일 사이즈 제한(bytes)
     */
    var sizeLimit: DataSize?,

    /**
     * 허용 하는 파일 타입 목록
     */
    var allowContentTypes: Set<MediaType>?,

    /**
     * 허용 하는 파일 확장자 이름 목록
     */
    var allowFileExtensions: Set<String>?,
  )
}
