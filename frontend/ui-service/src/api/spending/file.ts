"use server"

import apiClient from "../api-client"

const path = (cardId: number) => `cards/${cardId}/usage/files`

export async function createCardUsageFile(cardId: number, file: SaveCardUsageFile) {
  const response = await apiClient.post(path(cardId), file)
  const location = response?.entity?.headers?.["location"]?.[0]
  const start = (location?.lastIndexOf("/") ?? 0) + 1
  return location?.substring(start)
}

export type SaveCardUsageFile = {
  description?: string
  fileKey: string
  policy: string
}
