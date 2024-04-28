"use server"

import { stringify } from "qs"
import apiClient from "../api-client"

const path = "spending/statistics"

export async function getSpendingStatistics<T>(subject: Subject, filter: SpendingStatisticsFilter) {
  const query = stringify(filter, { arrayFormat: "comma" })
  const response = await apiClient.get<SpendingStatistics[]>(`${path}/${subject}`, query)
  return response.entity?.body
}

export type Subject = "YEAR" | "MONTH" | "DAY_OF_MONTH" | "DAY_OF_WEEK" | "HOUR" | "TAG" | "USER"

export type SpendingStatisticsFilter = {
  search001?: string
  start?: string
  end?: string
}
export type SpendingStatistics = {
  subject: Subject
  totalAmount: number
  items: SpendingStatisticsItem[]
}

export type SpendingStatisticsItem = {
  name: any
  amount: number
  count: number
}

export type DayOfMonth = "SUNDAY" | "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY"
