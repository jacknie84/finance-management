"use server"

import { handleErrorWithFormState } from "@/api/handlers"
import { SaveSpendingLog, postSpendingLog, putSpendingLog } from "@/api/spending/log"
import { parseMoney } from "@/lib/format"
import { isEmpty } from "@/lib/utils"
import moment from "moment"
import { revalidatePath } from "next/cache"
import { redirect } from "next/navigation"
import formFactory from "./factory"

export async function saveSpendingLog(id: string | number | undefined, _prev: unknown, formData: FormData) {
  const result = await formFactory.validateFormData(formData)
  if (isEmpty(result.errors)) {
    var log = {
      summary: formData.get("summary"),
      amount: parseMoney((formData.get("amount") as string) ?? "0"),
      time: moment(`${formData.get("date")}T${formData.get("time")}`).format(),
      tags: formData.getAll("tags"),
      username: formData.get("username"),
    } as SaveSpendingLog
    try {
      await (id ? putSpendingLog(id, log) : postSpendingLog(log))
      revalidatePath("/spending/logs/form")
      redirect("/spending/logs")
    } catch (e: any) {
      return handleErrorWithFormState(e, result)
    }
  }
  return result
}
