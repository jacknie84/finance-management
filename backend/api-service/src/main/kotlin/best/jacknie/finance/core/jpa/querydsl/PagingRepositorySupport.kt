package best.jacknie.finance.core.jpa.querydsl

import best.jacknie.finance.core.web.filter.PredefinedCondition
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*
import kotlin.reflect.KClass

abstract class PagingRepositorySupport(type: KClass<*>): QuerydslRepositorySupport(type.java) {

  protected fun <T> getPage(entityPath: EntityPath<T>, pageable: Pageable, predicate: () -> Predicate?): Page<T> {
    return getPage(from(entityPath), pageable, predicate)
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

  protected fun <T : Enum<T>> getPredicate(condition: PredefinedCondition<T>, booleanExpression: (T) -> BooleanExpression?): Predicate? {
    val operation = when (condition.reducing) {
      PredefinedCondition.Reducing.AND -> BooleanExpression::and
      PredefinedCondition.Reducing.OR -> BooleanExpression::or
    }
    return condition.items
      ?.mapNotNull { booleanExpression(it) }
      ?.takeIf { it.isNotEmpty() }
      ?.reduce(operation)
  }
}
