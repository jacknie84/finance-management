import { createCard } from "@/api/common/card"
import { UsersContext } from "@/contexts"
import useSystemModal from "@/hooks/system-modal"
import { Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader } from "@nextui-org/react"
import { createFormFactory } from "@tanstack/react-form"
import { useContext, useEffect, useState } from "react"
import CardIssuerField from "./CardIssuerField"
import CardNameField from "./CardNameField"
import CardNumberField from "./CardNumberField"
import UserField from "./UserField"
import { FormModel } from "./type"

const factory = createFormFactory<FormModel>({
  defaultValues: {
    name: "",
    number: "",
    username: "",
    issuer: "KB",
  },
})

type Events = { onSubmitted: () => void }
type Props = {} & Events

export default function CardRegisterButton({ onSubmitted }: Props) {
  const [isOpen, setIsOpen] = useState(false)
  const { users = [] } = useContext(UsersContext)
  const systemModal = useSystemModal()
  const form = factory.useForm({
    onSubmit: async ({ value }) => {
      try {
        await createCard(value)
      } catch (e: any) {
        systemModal.serverActionError(e.message)
      }
    },
  })
  const isSubmitted = form.useStore((state) => state.isSubmitted)

  useEffect(() => {
    if (isSubmitted) {
      setIsOpen(false)
      onSubmitted()
    }
  }, [isSubmitted, onSubmitted])

  return (
    <>
      <Button size="lg" color="primary" onClick={() => setIsOpen(true)}>
        카드 등록
      </Button>
      <Modal isOpen={isOpen} onOpenChange={setIsOpen}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">카드 등록</ModalHeader>
              <ModalBody>
                <CardNameField form={form} />
                <CardNumberField form={form} />
                <CardIssuerField form={form} />
                <UserField form={form} users={users} />
              </ModalBody>
              <ModalFooter>
                <Button color="default" onClick={onClose}>
                  취소
                </Button>
                <Button color="primary" onClick={() => form.handleSubmit()}>
                  확인
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  )
}
