package best.jacknie.finance.core.utils.pagination

interface Pager<T> {

  /**
   * 초기화 페이지 조회
   */
  fun initPage(): Page<T>

  /**
   * 다음 페이지 조회
   */
  fun nextPage(): Page<T>
}
