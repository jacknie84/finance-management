"use server"

import { getUsers } from "@/api/common/user"
import { getSpendingLog } from "@/api/spending/log"
import { getSpendingLogTagPreset } from "@/api/spending/tag"
import SpendingLogForm from "./_components/SpendingLogForm"

type Props = { searchParams: { id?: string } }

export default async function SpendingLogFormPage({ searchParams }: Props) {
  const { id } = searchParams
  const log = id ? await getSpendingLog(id) : undefined
  const preset = await getSpendingLogTagPreset()
  const users = await getUsers()

  return <SpendingLogForm data={log} users={users} tags={preset?.tags} />
}
