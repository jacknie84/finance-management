import apiClient from "@/api/core/api-client"

export async function getSpendingLogsPage() {
  return await apiClient.get("spending/logs")
}
