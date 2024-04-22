package best.jacknie.finance.spending.log.application.service

import best.jacknie.finance.spending.log.domain.SpendingTime
import java.time.ZonedDateTime

fun getSpendingTime(time: ZonedDateTime): SpendingTime {
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
