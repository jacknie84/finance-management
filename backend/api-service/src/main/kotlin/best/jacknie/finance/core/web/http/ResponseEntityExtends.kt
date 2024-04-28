package best.jacknie.finance.core.web.http

import best.jacknie.finance.common.file.domain.FileObject
import org.springframework.core.io.InputStreamResource
import org.springframework.http.CacheControl
import org.springframework.http.ContentDisposition
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.nio.charset.StandardCharsets
import java.time.Duration

fun ResponseEntity.BodyBuilder.binary(fileObject: FileObject): ResponseEntity<InputStreamResource> {
  val contentType = MediaType.parseMediaType(fileObject.contentType)
  val contentDispositionBuilder = if (contentType == MediaType.APPLICATION_OCTET_STREAM) {
    ContentDisposition.attachment()
  } else {
    ContentDisposition.inline()
  }
  val contentDisposition = contentDispositionBuilder
    .filename(fileObject.filename, StandardCharsets.UTF_8)
    .build()
  val cacheControl = CacheControl.maxAge(Duration.ofDays(30)).mustRevalidate().noTransform()
  val body = InputStreamResource(fileObject.content)
  return headers {
      it.contentType = contentType
      it.contentDisposition = contentDisposition
      it.contentLength = fileObject.fileSize
      it.setCacheControl(cacheControl)
      it.setLastModified(fileObject.lastModifiedDate)
    }
    .body(body)
}
