import apiClient from "../api-client"

const path = "spending/log/tags"

export async function getSpendingLogTagPreset() {
  const response = await apiClient.get<SpendingLogTagPreset>(`${path}/preset`)
  return response.entity?.body
}

export type SpendingLogTagPreset = {
  tags: string[]
}
