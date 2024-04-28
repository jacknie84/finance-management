"use server"

import { getSpendingLogsPage } from "@/api/spending/log"
import { getSpendingLogTagPreset } from "@/api/spending/tag"
import { PageRequest } from "@/api/types"
import SpendingTags from "./_components/SpendingTags"

export default async function SpendingTagsPage() {
  const filter = {}
  const pageRequest = { page: 0, size: 10, sort: { fields: ["id"], direction: "desc" } } as PageRequest
  const [page, preset] = await Promise.all([getSpendingLogsPage(filter, pageRequest), getSpendingLogTagPreset()])

  return <SpendingTags pageRequest={pageRequest} page={page} preset={preset} />
}
