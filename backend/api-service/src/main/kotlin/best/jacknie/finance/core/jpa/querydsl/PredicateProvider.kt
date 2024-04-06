package best.jacknie.finance.core.jpa.querydsl

import com.querydsl.core.types.Predicate

interface PredicateProvider {

  /**
   * QueryDSL 쿼리 조건
   */
  val predicate: Predicate?
}
