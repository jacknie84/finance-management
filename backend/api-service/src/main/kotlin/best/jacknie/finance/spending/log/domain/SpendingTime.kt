package best.jacknie.finance.spending.log.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.*

@Embeddable
data class SpendingTime(

  /**
   * 시간
   */
  var instant: Instant,

  /**
   * 타임 존 아이디
   */
  var zone: ZoneId,

  /**
   * 연도
   */
  var year: Int,

  /**
   * 월
   */
  @Enumerated(value = EnumType.STRING)
  var month: Month,

  /**
   * 일
   */
  var dayOfMonth: Int,

  /**
   * 요일
   */
  @Enumerated(value = EnumType.STRING)
  var dayOfWeek: DayOfWeek,

  /**
   * 시간
   */
  var hour: Int,
) {

  companion object {

    fun from(time: ZonedDateTime): SpendingTime {
      return SpendingTime(
        instant = time.toInstant(),
        zone = time.zone,
        year = time.year,
        month = time.month,
        dayOfMonth = time.dayOfMonth,
        dayOfWeek = time.dayOfWeek,
        hour = time.hour,
      )
    }
  }
}
