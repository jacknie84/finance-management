package best.jacknie.finance.common.file.application.port

import best.jacknie.finance.common.file.domain.QFileMetadataEntity.fileMetadataEntity
import best.jacknie.finance.core.jpa.querydsl.PredicateProvider
import com.querydsl.core.types.Predicate

data class FileMetadataFilter(

  /**
   * 파일 키 목록
   */
  var key: Set<String>? = null,

): PredicateProvider {

  override val predicate: Predicate? get() {
    return key?.let { fileMetadataEntity.key.`in`(it) }
  }
}
