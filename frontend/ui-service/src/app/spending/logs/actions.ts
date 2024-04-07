"use server"

import apiClient, { buildPageRequest } from "@/api/api-client"
import { Page, PageRequest } from "@/api/types"
import { isEmpty } from "@/lib/utils"
import { stringify } from "qs"

function buildSpendingLogsFilter(filter: SpendingLogsFilter) {
  return stringify(filter, { arrayFormat: "comma" })
}

export async function getSpendingLogsPage(filter: SpendingLogsFilter, pageRequest: PageRequest) {
  const queries = [buildSpendingLogsFilter(filter), buildPageRequest(pageRequest)]
  const query = queries.filter((it) => !isEmpty(it)).join("&")
  const response = await apiClient.get<Page<SpendingLog>>("spending/logs", query)
  console.log(query)
  return response.entity?.body
}

export type SpendingLog = {
  id: string | number
  summary?: string
  amount: number
  time: { instant: string }
}

export type SpendingLogsFilter = {
  containsSummary?: string[]
}
