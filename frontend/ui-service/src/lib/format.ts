import moment, { Moment, MomentInput } from "moment"
import numeral from "numeral"

export function formatMoney(amount?: number, defaultValue: string = "-") {
  return amount ? numeral(amount).format("0,0") : defaultValue
}

export function parseMoney(value?: string) {
  return value ? numeral(value).value() : NaN
}

export function formatDate(dateTime?: MomentInput | Moment, defaultValue: string = "-") {
  return dateTime ? moment(dateTime).format("YYYY-MM-DD") : defaultValue
}

export function formatTime(dateTime?: MomentInput | Moment, defaultValue: string = "-") {
  return dateTime ? moment(dateTime).format("HH:mm") : defaultValue
}
