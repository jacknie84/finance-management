package best.jacknie.finance.core.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*
import kotlin.reflect.KClass

abstract class PagingRepositorySupport(type: KClass<*>): QuerydslRepositorySupport(type.java) {

  protected fun <T> getPage(entityPath: EntityPath<T>, filter: PredicateProvider, pageable: Pageable): Page<T> {
    return getPage(entityPath, pageable) { filter.predicate }
  }

  protected fun <T> getPage(entityPath: EntityPath<T>, pageable: Pageable, predicate: () -> Predicate?): Page<T> {
    return getPage(from(entityPath), pageable, predicate)
  }

  protected fun <T> getPage(query: JPQLQuery<T>, filter: PredicateProvider, pageable: Pageable): Page<T> {
    return getPage(query, pageable) { filter.predicate }
  }

  protected fun <T> getPage(query: JPQLQuery<T>, pageable: Pageable, predicate: () -> Predicate?): Page<T> {
    val localQuery = query.where(predicate())
    val total = localQuery.fetchCount()
    if (total > 0) {
      val content = querydsl?.applyPagination(pageable, localQuery)?.fetch() ?: Collections.emptyList()
      return PageImpl(content, pageable, total)
    } else {
      return Page.empty(pageable)
    }
  }
}
