import moment from "moment"
import numeral from "numeral"

export function formatMoney(amount?: number) {
  return amount ? numeral(amount).format("0,0") : "-"
}

export function formatDate(dateTime?: string) {
  return dateTime ? moment(dateTime).format("YYYY-MM-DD") : "-"
}

export function formatTime(dateTime?: string) {
  return dateTime ? moment(dateTime).format("HH:mm:ss") : "-"
}
