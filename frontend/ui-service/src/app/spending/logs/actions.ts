"use server"

import apiClient from "@/api/core/api-client"
import { Page } from "@/api/core/types"

export async function getSpendingLogsPage() {
  const response = await apiClient.get<Page<SpendingLog>>("spending/logs")
  return response.entity?.body
}

export type SpendingLog = {
  id: string | number
  summary?: string
  amount: number
  time: { instant: string }
}
