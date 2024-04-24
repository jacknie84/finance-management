package best.jacknie.finance.core.web.filter

interface PredefinedCondition<T : Enum<T>> {

  /**
   * 미리 정의된 검색 조건 목록
   */
  var items: Set<T>?

  /**
   * 검색 조건 목록 누적 연산 타입
   */
  var reducing: Reducing

  enum class Reducing {
    /**
     * AND 연산으로 조건을 누적 연산
     */
    AND,

    /**
     * OR 연산으로 조건을 누적 연산
     */
    OR,
  }
}
