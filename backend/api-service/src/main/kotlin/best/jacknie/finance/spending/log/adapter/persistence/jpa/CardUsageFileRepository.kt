package best.jacknie.finance.spending.log.adapter.persistence.jpa

import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CardUsageFileRepository: JpaRepository<CardUsageFileEntity, Long>, CardUsageFileCustomRepository {

  /**
   * 카드 사용 내역 업로드 파일 정보 조회
   */
  fun findByCardIdAndId(cardId: Long, id: Long): CardUsageFileEntity?
}
