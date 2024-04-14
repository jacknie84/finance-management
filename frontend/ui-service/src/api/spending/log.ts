"use server"

import apiClient, { buildPageRequest } from "@/api/api-client"
import { Page, PageRequest } from "@/api/types"
import { isEmpty } from "@/lib/utils"
import { stringify } from "qs"

const path = "spending/logs"

function buildSpendingLogsFilter(filter: SpendingLogsFilter) {
  return stringify(filter, { arrayFormat: "comma" })
}

export async function getSpendingLogsPage(filter: SpendingLogsFilter, pageRequest: PageRequest) {
  const queries = [buildSpendingLogsFilter(filter), buildPageRequest(pageRequest)]
  const query = queries.filter((it) => !isEmpty(it)).join("&")
  const response = await apiClient.get<Page<SpendingLog>>(path, query)
  return response.entity?.body
}

export async function getSpendingLog(id: string | number) {
  const response = await apiClient.get<SpendingLog>(`${path}/${id}`)
  return response.entity?.body
}

export async function postSpendingLog(log: SaveSpendingLog) {
  await apiClient.post<SaveSpendingLog>(path, log)
}

export async function putSpendingLog(id: string | number, log: SaveSpendingLog) {
  await apiClient.put<SaveSpendingLog>(`${path}/${id}`, log)
}

export async function deleteSpendingLog(id: string | number) {
  await apiClient.delete<SaveSpendingLog>(`${path}/${id}`)
}

export type SpendingLog = {
  id: string | number
  summary?: string
  amount: number
  time: string
  tags?: string[]
  user: { name: string }
}

export type SpendingLogsFilter = {
  search001?: string[]
}

export type SaveSpendingLog = {
  summary?: string
  amount: number
  time: string
  tags: string[]
  username: string
}
