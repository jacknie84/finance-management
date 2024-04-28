package best.jacknie.finance.core.utils.pagination

fun interface Pagination {

  /**
   * 다음 페이지 존재 여부 확인
   */
  fun hasNextPage(): Boolean
}
