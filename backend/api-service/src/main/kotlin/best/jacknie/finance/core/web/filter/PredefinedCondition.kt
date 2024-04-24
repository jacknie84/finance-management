package best.jacknie.finance.core.web.filter

data class PredefinedCondition(

  /**
   * 미리 정의된 검색 조건 목록
   */
  val items: List<String>? = null,

  /**
   * 검색 조건 목록 누적 연산 타입
   */
  val reducing: Reducing = Reducing.OR,

) {

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

  companion object {

    fun single(item: String): List<PredefinedCondition> {
      return listOf(PredefinedCondition(listOf(item)))
    }
  }
}
