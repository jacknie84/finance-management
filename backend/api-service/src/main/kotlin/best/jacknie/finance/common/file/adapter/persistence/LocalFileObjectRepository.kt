package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.domain.FilePolicy
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.*
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Repository
class LocalFileObjectRepository: FileObjectRepository {

  @Value("\${app.file.local.base-path}")
  lateinit var basePath: String

  override fun save(file: MultipartFile, policy: FilePolicy): String {
    val today = LocalDate.now()
    val yearMonth = YearMonth.from(today)
    val keyPath = Paths.get(policy.name, "$yearMonth", "${today.dayOfMonth}".padStart(2, '0'))
    val directory = Files.createDirectories(Paths.get(basePath).resolve(keyPath))
    val extension = StringUtils.getFilenameExtension(file.originalFilename)
    validateFilePolicy(file, extension, policy)
    var physicalName: String
    var path: Path
    do {
      physicalName = "${UUID.randomUUID()}${extension?.let { ".$it" } ?: ""}"
      path = directory.resolve(physicalName)
    } while (Files.exists(path, LinkOption.NOFOLLOW_LINKS));
    Files.copy(file.inputStream, path, StandardCopyOption.REPLACE_EXISTING)
    return StringUtils.cleanPath(keyPath.resolve(physicalName).toString())
  }

  override fun load(key: String): LoadedFile {
    val tempPath = Files.createTempFile(UUID.randomUUID().toString(), null)
    val path = Paths.get(basePath, key)
    Files.copy(path, tempPath, StandardCopyOption.REPLACE_EXISTING)
    val size = tempPath.toFile().length()
    val inputStream = Files.newInputStream(tempPath, StandardOpenOption.DELETE_ON_CLOSE)
    return LoadedFile(size, inputStream)
  }

  private fun validateFilePolicy(file: MultipartFile, ext: String?, policy: FilePolicy) {
    if (policy.sizeLimit != null && policy.sizeLimit.toBytes() < file.size) {
      error("파일 사이즈 제한 오류(size: ${file.size}")
    }
    if (ext.isNullOrBlank()) {
      if (policy.allowFileExtensions.isNullOrEmpty()) {
        return
      }
    }
    if (policy.allowFileExtensions.isNullOrEmpty()) {
      if (!ext.isNullOrBlank()) {
        error("허용 하는 파일 확장자 이름이 없습니다($ext)")
      }
    } else {
      if (policy.allowFileExtensions.none { it.equals(ext, true) }) {
        error("허용 하지 않는 파일 확장자 이름 입니다($ext)")
      }
    }
    if (policy.allowContentTypes.isNullOrEmpty()) {
      if (!file.contentType.isNullOrBlank()) {
        error("허용 하는 파일 타입이 없습니다(${file.contentType})")
      }
    } else {
      if (file.contentType.isNullOrBlank()) {
        error("허용 하는 파일 타입만 업로드 가능 합니다")
      }
      if (policy.allowContentTypes.none { it.includes(MediaType.parseMediaType(file.contentType!!)) }) {
        error("허용 하는 파일 타입만 업로드 가능 합니다(${file.contentType})")
      }
    }
  }
}
