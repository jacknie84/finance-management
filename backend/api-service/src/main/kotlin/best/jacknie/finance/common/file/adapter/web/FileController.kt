package best.jacknie.finance.common.file.adapter.web

import best.jacknie.finance.common.file.application.port.FileMetadataFilter
import best.jacknie.finance.common.file.application.port.FileService
import best.jacknie.finance.common.file.application.port.UploadFile
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/files")
class FileController(
  private val fileService: FileService
) {

  @PutMapping
  fun uploadFile(@Valid dto: UploadFile): ResponseEntity<*> {
    val entity = fileService.uploadFile(dto)
    return ResponseEntity.ok(entity)
  }

  @GetMapping
  fun getFileMetadataPage(@Valid filter: FileMetadataFilter, pageable: Pageable): ResponseEntity<*> {
    val page = fileService.getFileMetadataPage(filter, pageable)
    return ResponseEntity.ok(page)
  }
}
