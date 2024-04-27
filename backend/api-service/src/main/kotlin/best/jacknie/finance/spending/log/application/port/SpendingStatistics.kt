package best.jacknie.finance.spending.log.application.port

import com.querydsl.core.annotations.QueryProjection

data class SpendingStatistics(

  /**
   * 통계 주제
   */
  val subject: Subject,

  /**
   * 통계 총 지출 금액
   */
  val totalAmount: Int,

  /**
   * 통계 항목 목록
   */
  val items: List<Item>

) {

  enum class Subject {

    /**
     * 연별
     */
    YEAR,

    /**
     * 월별
     */
    MONTH,

    /**
     * 시간 별
     */
    HOUR,

    /**
     * 일별
     */
    DAY_OF_MONTH,

    /**
     * 요일별
     */
    DAY_OF_WEEK,

    /**
     * 태그별
     */
    TAG,

    /**
     * 사용자별
     */
    USER,
  }

  data class Item @QueryProjection constructor(

    /**
     * 항목 이름
     */
    val name: Any,

    /**
     * 지출 금액 합계
     */
    val amount: Int,

    /**
     * 지출 회수
     */
    val count: Long,
  )
}
