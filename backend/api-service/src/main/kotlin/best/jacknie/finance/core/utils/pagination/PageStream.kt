package best.jacknie.finance.core.utils.pagination

class PageStream<T>(private val pager: Pager<T>): Iterable<T> {

  override fun iterator(): Iterator<T> {
    return IteratorImpl(pager)
  }

  private class IteratorImpl<T>(private val pager: Pager<T>): Iterator<T> {

    private var delegate: Iterator<T>? = null
    private var pagination: Pagination? = null

    override fun hasNext(): Boolean {
      if (delegate != null && delegate!!.hasNext()) {
        return true
      }
      if (pagination == null) {
        val delegate = loadPage(pager::initPage)
        return delegate.hasNext()
      }
      if (pagination!!.hasNextPage()) {
        val delegate = loadPage(pager::nextPage)
        return delegate.hasNext()
      }
      return false
    }

    override fun next(): T {
      if (delegate == null) {
        val delegate = loadPage(pager::initPage)
        return delegate.next()
      } else {
        return delegate!!.next()
      }
    }

    private fun loadPage(getPage: () -> Page<T>): Iterator<T> {
      val page = getPage()
      val delegate = page.content.iterator()
      this.delegate = delegate
      this.pagination = page.pagination
      return delegate
    }
  }
}
