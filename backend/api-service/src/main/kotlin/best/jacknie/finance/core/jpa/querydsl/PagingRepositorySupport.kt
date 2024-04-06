package best.jacknie.finance.core.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*
import kotlin.reflect.KClass

abstract class PagingRepositorySupport(type: KClass<*>): QuerydslRepositorySupport(type.java) {

  protected fun <T> getPage(entityPath: EntityPath<T>, filter: PredicateProvider, pageable: Pageable): Page<T> {
    val query = from(entityPath).where(filter.predicate)
    val total = query.fetchCount()
    if (total > 0) {
      val content = querydsl?.applyPagination(pageable, query)?.fetch() ?: Collections.emptyList()
      return PageImpl(content, pageable, total)
    } else {
      return Page.empty(pageable)
    }
  }

  protected fun <T> getPage(entityPath: EntityPath<T>, pageable: Pageable, predicate: () -> Predicate?): Page<T> {
    val query = from(entityPath).where(predicate())
    val total = query.fetchCount()
    if (total > 0) {
      val content = querydsl?.applyPagination(pageable, query)?.fetch() ?: Collections.emptyList()
      return PageImpl(content, pageable, total)
    } else {
      return Page.empty(pageable)
    }
  }
}
