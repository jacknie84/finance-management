import { redirect } from "next/navigation"

export default async function SpendingPage() {
  redirect("/spending/logs")
}
