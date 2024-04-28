package best.jacknie.finance.spending.log.domain

import best.jacknie.finance.spending.log.application.port.SaveSpendingTime
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

    fun from(time: SaveSpendingTime): SpendingTime {
      val zonedDateTime = ZonedDateTime.ofInstant(time.instant, time.zone)
      return SpendingTime(
        instant = time.instant,
        zone = time.zone,
        year = zonedDateTime.year,
        month = zonedDateTime.month,
        dayOfMonth = zonedDateTime.dayOfMonth,
        dayOfWeek = zonedDateTime.dayOfWeek,
        hour = zonedDateTime.hour,
      )
    }
  }
}
