"use client"

import { User } from "@/api/common/user"
import Container from "@/components/Container"
import { UsersContext } from "@/contexts"
import { Button } from "@nextui-org/react"
import { createFormFactory } from "@tanstack/react-form"
import { useRouter } from "next/navigation"
import { useState } from "react"
import CardField from "./CardField"
import CardUsageFileDescriptionField from "./CardUsageFielDescriptionField"
import CardUsageFileField from "./CardUsageFileField"
import CardUsagesField from "./CardUsagesField"
import CardUsagesSubmitModal from "./CardUsagesSubmitModal"
import { FormModel } from "./type"

const factory = createFormFactory<FormModel>({
  defaultValues: {
    usages: [],
  },
})

type Props = { users?: User[] }

export default function SpendingCardUsageForm({ users }: Props) {
  const form = factory.useForm({
    onSubmit: async ({ value }) => setModel(value),
  })
  const isPristine = form.useStore((state) => state.isPristine)
  const isInvalid = form.useStore((state) => !state.isValid)
  const isPending = form.useStore((state) => state.isSubmitting)
  const router = useRouter()
  const [cardSelected, setCardSelected] = useState(false)
  const [model, setModel] = useState<FormModel>()

  return (
    <Container>
      <div className="space-y-8">
        <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
          <UsersContext.Provider value={{ users }}>
            <CardField form={form} onSelected={() => setCardSelected(true)} />
          </UsersContext.Provider>
        </div>
        {cardSelected && (
          <>
            <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
              <CardUsageFileField form={form} />
              <CardUsageFileDescriptionField form={form} />
            </div>
            <div className="flex w-full flex-wrap md:flex-nowrap mb-6 md:mb-0 gap-4">
              <CardUsagesField form={form} />
            </div>
            <div className="flex justify-end gap-4">
              <Button color="default" onClick={() => router.replace("/spending/card")}>
                취소
              </Button>
              <Button color="primary" isDisabled={isPending || isPristine || isInvalid} onClick={() => form.handleSubmit()}>
                저장
              </Button>
              <CardUsagesSubmitModal model={model} onClose={() => setModel(undefined)} />
            </div>
          </>
        )}
      </div>
    </Container>
  )
}
