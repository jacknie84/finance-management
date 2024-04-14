"use server"

import { getUsers } from "@/api/common/user"
import SpendingCardUsageForm from "./_components/SpendingCardUsageForm"

export default async function SpendingCardUsageFormPage() {
  const users = await getUsers()

  return <SpendingCardUsageForm users={users} />
}
