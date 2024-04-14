"use server"

import { isEmpty } from "@/lib/utils"
import { stringify } from "qs"
import apiClient, { buildPageRequest } from "../api-client"
import { Card } from "../common/card"
import { Page, PageRequest } from "../types"
import { SpendingLog } from "./log"

const path = "card/usages"
const pathWithCardId = (cardId: string | number) => `cards/${cardId}/usages`

export async function getCardUsagesPage(filter: CardUsagesFilter, pageRequest: PageRequest) {
  const queries = [buildCardUsageFilter(filter), buildPageRequest(pageRequest)]
  const query = queries.filter((it) => !isEmpty(it)).join("&")
  const response = await apiClient.get<Page<CardUsage>>(path, query)
  return response.entity?.body
}

export async function createCardUsage(cardId: string | number, usage: SaveCardUsage) {
  await apiClient.post(pathWithCardId(cardId), usage)
}

export async function putCardUsage(cardId: string | number, id: string | number, usage: SaveCardUsage) {
  await apiClient.put(`${pathWithCardId(cardId)}/${id}`, usage)
}

function buildCardUsageFilter(filter: CardUsagesFilter) {
  return stringify(filter, { arrayFormat: "comma" })
}

export type CardUsage = {
  id: string | number
  approvalNumber: string
  merchant: string
  status: CardUsageStatus
  file: CardUsageFile
  log: SpendingLog
}

export type CardUsageStatus = "NOT_INVOICED" | "INVOICED" | "CANCELLED_INVOICED" | "APPROVAL_CANCELLED"

export type CardUsageFile = {
  id: string | number
  description?: string
  fileKey: string
  card: Card
}

export type CardUsagesFilter = {
  approvalNumber?: string[]
  search001?: string[]
}

export type SaveCardUsage = {
  approvalNumber: string
  merchant: string
  status: CardUsageStatus
  amount: number
  time: string
  tags?: string[]
  fileId: string | number
}
