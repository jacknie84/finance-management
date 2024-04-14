import { formatDate, formatTime } from "@/lib/format"
import { createFormFactory } from "@tanstack/react-form"
import { FormModel } from "./_components/types"

export default createFormFactory<FormModel>({
  defaultValues: {
    summary: "",
    amount: 0,
    date: formatDate(new Date()),
    time: formatTime(new Date()),
    username: "",
    tags: [],
  },
  onServerValidate: ({ value }) => {
    return undefined
  },
})
