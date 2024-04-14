"use server"

import { CardUsagesFilter, getCardUsagesPage } from "@/api/spending/card"
import { PageRequest } from "@/api/types"
import SpendingCardUsages from "./_components/SpendingCardUsages"

export default async function SpendingCardPage() {
  const filter = {} as CardUsagesFilter
  const pageRequest = { page: 0, size: 10, sort: { fields: ["id"], direction: "desc" } } as PageRequest
  const page = await getCardUsagesPage(filter, pageRequest)

  return <SpendingCardUsages pageRequest={pageRequest} page={page} />
}
