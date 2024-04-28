"use server"

import { SpendingLogsFilter, getSpendingLogsPage } from "@/api/spending/log"
import { PageRequest } from "@/api/types"
import SpendingLogs from "./_components/SpendingLogs"

export default async function SpendingLogsPage() {
  const filter = {} as SpendingLogsFilter
  const pageRequest = { page: 0, size: 10, sort: { fields: ["id"], direction: "desc" } } as PageRequest
  const page = await getSpendingLogsPage(filter, pageRequest)

  return <SpendingLogs pageRequest={pageRequest} page={page} />
}
