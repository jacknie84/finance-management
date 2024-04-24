package best.jacknie.finance.common.file.adapter.persistence

import best.jacknie.finance.common.file.application.port.FileMetadataCustomRepository
import best.jacknie.finance.common.file.application.port.FileMetadataFilter
import best.jacknie.finance.common.file.domain.FileMetadataEntity
import best.jacknie.finance.common.file.domain.QFileMetadataEntity.fileMetadataEntity
import best.jacknie.finance.core.jpa.querydsl.PagingRepositorySupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository("fileMetadataRepositoryImpl")
class FileMetadataCustomRepositoryImpl: PagingRepositorySupport(FileMetadataEntity::class),
    FileMetadataCustomRepository {

  override fun findAll(filter: FileMetadataFilter, pageable: Pageable): Page<FileMetadataEntity> {
    return getPage(fileMetadataEntity, pageable) { filter.key?.let { fileMetadataEntity.key.`in`(it) } }
  }
}
