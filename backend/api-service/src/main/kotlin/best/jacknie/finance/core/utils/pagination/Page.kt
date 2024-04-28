package best.jacknie.finance.core.utils.pagination

interface Page<T> {

  /**
   * 페이지 목록
   */
  val content: List<T>

  /**
   * 페이지네이션 정보
   */
  val pagination: Pagination
}
