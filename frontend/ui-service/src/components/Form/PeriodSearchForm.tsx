import { formatDate, formatTime, parseDate, parseTime } from "@/lib/format"
import { isEmpty } from "@/lib/utils"
import { Period } from "@/types"
import { Button } from "@nextui-org/react"
import moment, { DurationInputArg1, DurationInputArg2, Moment } from "moment"
import { useCallback, useState } from "react"
import DateInput from "./DateInput"
import TimeInput from "./TimeInput"

type Events = { onChange: (period: Period) => void }
type Props = {} & Events
type LocalDate = { year: number; month: number; day: number }
type LocalTime = { hour: number; minute: number }
type LocalDateTime = { date?: LocalDate; time?: LocalTime }

function toLocalDate(date: string) {
  const parsed = parseDate(date)
  if (parsed) {
    const year = parsed.get("year")
    const month = parsed.get("month")
    const day = parsed.get("day")
    return { year, month, day }
  }
}

function toLocalTime(time: string) {
  const parsed = parseTime(time)
  if (parsed) {
    const hour = parsed.get("hour")
    const minute = parsed.get("minute")
    return { hour, minute }
  }
}

function toLocalDateTime(moment: Moment) {
  const year = moment.get("year")
  const month = moment.get("month")
  const day = moment.get("date")
  const hour = moment.get("hour")
  const minute = moment.get("minute")
  return { date: { year, month, day }, time: { hour, minute } }
}

function toString({ date, time }: LocalDateTime) {
  const input = { ...date, ...time }
  if (!isEmpty(input)) {
    return moment(input).format()
  }
}

export default function PeriodSearchForm({ onChange }: Props) {
  const [startDate, setStartDate] = useState<LocalDate>()
  const [startTime, setStartTime] = useState<LocalTime>()
  const [endDate, setEndDate] = useState<LocalDate>()
  const [endTime, setEndTime] = useState<LocalTime>()

  const onClickSimplePeriod = useCallback(
    (amount: DurationInputArg1, unit: DurationInputArg2) => {
      const start = toLocalDateTime(moment().subtract(amount, unit))
      const end = toLocalDateTime(moment())
      setStartDate(start.date)
      setStartTime(start.time)
      setEndDate(end.date)
      setEndTime(end.time)
      onChange({ start: toString(start), end: toString(end) })
    },
    [onChange],
  )

  const onChangeStartDate = useCallback(
    (value: string) => {
      const date = toLocalDate(value)
      const start = { date, time: startTime }
      const end = { date: endDate, time: endTime }
      setStartDate(date)
      onChange({ start: toString(start), end: toString(end) })
    },
    [onChange, startTime, endDate, endTime],
  )

  const onChangeStartTime = useCallback(
    (value: string) => {
      const time = toLocalTime(value)
      const start = { date: startDate, time: time }
      const end = { date: endDate, time: endTime }
      setStartTime(time)
      onChange({ start: toString(start), end: toString(end) })
    },
    [onChange, startDate, endDate, endTime],
  )

  const onChangeEndDate = useCallback(
    (value: string) => {
      const date = toLocalDate(value)
      const start = { date: startDate, time: startTime }
      const end = { date, time: endTime }
      setEndDate(date)
      onChange({ start: toString(start), end: toString(end) })
    },
    [onChange, startDate, startTime, endTime],
  )

  const onChangeEndTime = useCallback(
    (value: string) => {
      const time = toLocalTime(value)
      const start = { date: startDate, time: startTime }
      const end = { date: endDate, time }
      setEndTime(time)
      onChange({ start: toString(start), end: toString(end) })
    },
    [onChange, startDate, startTime, endDate],
  )

  return (
    <div className="flex flex-col gap-4">
      <div className="flex justify-between gap-4 items-end">
        <div className="flex flex-wrap gap-4 items-start">
          <Button color="primary" onClick={() => onClickSimplePeriod(1, "day")}>
            1일
          </Button>
          <Button color="primary" onClick={() => onClickSimplePeriod(1, "week")}>
            1주일
          </Button>
          <Button color="primary" onClick={() => onClickSimplePeriod(1, "month")}>
            1개월
          </Button>
          <Button color="primary" onClick={() => onClickSimplePeriod(3, "month")}>
            3개월
          </Button>
          <Button color="primary" onClick={() => onClickSimplePeriod(6, "month")}>
            6개월
          </Button>
          <Button color="primary" onClick={() => onClickSimplePeriod(1, "year")}>
            1년
          </Button>
        </div>
        <Button
          color="secondary"
          onClick={() => {
            setStartDate(undefined)
            setStartTime(undefined)
            setEndDate(undefined)
            setEndTime(undefined)
            onChange({})
          }}
        >
          Reset
        </Button>
      </div>
      <div className="flex justify-between gap-4 items-end">
        <DateInput label="날짜" value={formatDate(startDate, "")} onValueChange={onChangeStartDate} />
        <TimeInput label="시간" value={formatTime(startTime, "")} onValueChange={onChangeStartTime} />
        <p>~</p>
        <DateInput label="날짜" value={formatDate(endDate, "")} onValueChange={onChangeEndDate} />
        <TimeInput label="시간" value={formatTime(endTime, "")} onValueChange={onChangeEndTime} />
      </div>
    </div>
  )
}
