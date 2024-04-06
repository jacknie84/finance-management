package best.jacknie.finance.common.card.application.port

import best.jacknie.finance.common.card.domain.CardUsageFileEntity
import best.jacknie.finance.common.file.domain.FileObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CardUsageFileService {

  /**
   * 카드 사용 내역 업로드 파일 정보 저장 처리
   */
  fun createCardUsageFile(cardId: Long, dto: SaveCardUsageFile): CardUsageFileEntity

  /**
   * 카드 사용 내역 업로드 파일 목록 페이지 조회
   */
  fun getCardUsageFilesPage(cardId: Long, pageable: Pageable): Page<CardUsageFileEntity>

  /**
   * 카드 사용 내역 업로드 파일 내용 조회
   */
  fun getCardUsageFileObject(cardId: Long, id: Long): FileObject

  /**
   * 카드 사용 내역 파일 분석 결과 조회
   */
  fun getCardUsages(cardId: Long, id: Long): List<RawCardUsage>
}
