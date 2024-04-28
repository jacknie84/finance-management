package best.jacknie.finance.core.utils.pagination

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class SpringDataPager<T>(private var pageable: Pageable, private val pager: (Pageable) -> org.springframework.data.domain.Page<T>): Pager<T> {

  override fun initPage(): Page<T> {
    val page = pager(pageable)
    return PageImpl(page)
  }

  override fun nextPage(): Page<T> {
    val pageNumber = pageable.pageNumber + 1
    val pageRequest = PageRequest.of(pageNumber, pageable.pageSize, pageable.sort)
    val page = pager(pageRequest)
    this.pageable = pageRequest
    return PageImpl(page)
  }

  private class PageImpl<T>(private val page: org.springframework.data.domain.Page<T>): Page<T> {

    override val content: MutableList<T> = page.content

    override val pagination = Pagination { page.number < page.totalPages.dec() }
  }
}
