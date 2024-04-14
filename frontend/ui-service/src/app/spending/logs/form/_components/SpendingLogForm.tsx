"use client"

import Container from "@/components/Container"

import { User } from "@/api/common/user"
import { SpendingLog } from "@/api/spending/log"
import useSystemModal from "@/hooks/system-modal"
import { formatDate, formatTime } from "@/lib/format"
import { isEmpty } from "@/lib/utils"
import { Button } from "@nextui-org/react"
import { mergeForm, useTransform } from "@tanstack/react-form"
import { useRouter } from "next/navigation"
import { useEffect } from "react"
import { useFormState, useFormStatus } from "react-dom"
import { saveSpendingLog } from "../actions"
import formFactory from "../factory"
import AmountField from "./AmountField"
import DateField from "./DateField"
import DeleteButton from "./DeleteButton"
import SummaryField from "./SummaryField"
import TagsField from "./TagsField"
import TimeField from "./TimeField"
import UserField from "./UserField"

type Props = { data?: SpendingLog; users?: User[]; tags?: string[] }

export default function SpendingLogForm({ data, users = [], tags = [] }: Props) {
  const [state, dispatch] = useFormState(saveSpendingLog.bind(null, data?.id), formFactory.initialFormState)
  const form = formFactory.useForm({ transform: useTransform((baseForm) => mergeForm(baseForm, state), [state]) })
  const isPristine = form.useStore((state) => state.isPristine)
  const isInvalid = form.useStore((state) => !state.isValid)
  const errors = form.useStore((state) => state.errors)
  const { pending: isPending } = useFormStatus()
  const router = useRouter()
  const systemModal = useSystemModal()

  useEffect(() => {
    if (!isEmpty(errors) && !isEmpty(errors[0])) {
      systemModal.serverActionError(<p className="break-all">{errors[0]}</p>)
    }
  }, [systemModal, errors])

  useEffect(() => {
    if (data && isPristine) {
      form.setFieldValue("summary", data.summary ?? "")
      form.setFieldValue("amount", data.amount)
      form.setFieldValue("date", formatDate(data.time))
      form.setFieldValue("time", formatTime(data.time))
      form.setFieldValue("tags", data.tags || [])
      form.setFieldValue("username", data.user.name)
    }
  }, [form, data, isPristine])

  return (
    <Container>
      <form action={dispatch}>
        <div className="space-y-8">
          <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
            <DateField form={form} />
            <TimeField form={form} />
          </div>
          <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
            <SummaryField form={form} />
            <AmountField form={form} />
            <UserField form={form} users={users} />
          </div>
          <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
            <TagsField form={form} preset={tags} />
          </div>
          <div className="flex justify-end gap-4">
            {data?.id && <DeleteButton id={data.id} />}
            <Button color="default" onClick={() => router.replace("/spending/logs")}>
              취소
            </Button>
            <Button color="primary" type="submit" isDisabled={isPending || isPristine || isInvalid}>
              저장
            </Button>
          </div>
        </div>
      </form>
    </Container>
  )
}
