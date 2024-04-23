"use server"

import { SpendingLog, patchSpendingLog } from "@/api/spending/log"
import { isEmpty } from "@/lib/utils"

export async function addSpendingLogTag(log: SpendingLog, tag: string) {
  const tags = log.tags ?? []
  if (!tags.includes(tag)) {
    await patchSpendingLog(log.id, { tags: [...tags, tag] })
  }
}

export async function removeSpendingLogTag(log: SpendingLog, tag: string) {
  const tags = log.tags ?? []
  if (tags.includes(tag)) {
    await patchSpendingLog(log.id, { tags: tags.filter((it) => tag !== it) })
  }
}

export async function emptySpendingLogTag(log: SpendingLog) {
  const tags = log.tags ?? []
  if (!isEmpty(tags)) {
    await patchSpendingLog(log.id, { tags: [] })
  }
}
