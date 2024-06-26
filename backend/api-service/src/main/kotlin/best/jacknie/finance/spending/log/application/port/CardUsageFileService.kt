package best.jacknie.finance.spending.log.application.port

import best.jacknie.finance.common.file.domain.FileObject
import best.jacknie.finance.spending.log.domain.CardUsageFileEntity
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
}
