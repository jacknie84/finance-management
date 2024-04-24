"use server"

import apiClient, { buildPageRequest, buildPredefinedConditions } from "@/api/api-client"
import { Page, PageRequest, PredefinedCondition } from "@/api/types"
import { isNotEmpty } from "@/lib/utils"
import { stringify } from "qs"

const path = "spending/logs"

function buildSpendingLogsFilter(filter: SpendingLogsFilter) {
  const { search001, start, end, conditions } = filter
  const prefix = stringify({ search001, start, end }, { arrayFormat: "comma" })
  const suffix = buildPredefinedConditions(conditions)
  return [prefix, suffix].filter(isNotEmpty).join("&")
}

export async function getSpendingLogsPage(filter: SpendingLogsFilter, pageRequest: PageRequest) {
  const queries = [buildSpendingLogsFilter(filter), buildPageRequest(pageRequest)]
  const query = queries.filter(isNotEmpty).join("&")
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

export async function patchSpendingLog(id: string | number, log: PatchSpendingLog) {
  await apiClient.patch<PatchSpendingLog>(`${path}/${id}`, log)
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
  start?: string
  end?: string
  conditions?: PredefinedCondition<SpendingLogsQueryCondition>[]
}

export type SpendingLogsQueryCondition = "EMPTY_TAGS"

export type SaveSpendingLog = {
  summary?: string
  amount: number
  time: string
  tags: string[]
  username: string
}

export type PatchSpendingLog = Partial<SaveSpendingLog>
