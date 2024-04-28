import { deleteSpendingLog } from "@/api/spending/log"
import ConfirmModal from "@/components/ConfirmModal"
import { Button } from "@nextui-org/react"
import { useRouter } from "next/navigation"
import { useState } from "react"

type Props = { id: string | number }

export default function DeleteButton({ id }: Props) {
  const [isOpen, setIsOpen] = useState(false)
  const router = useRouter()

  return (
    <>
      <Button color="danger" onClick={() => setIsOpen(true)}>
        삭제
      </Button>
      <ConfirmModal
        isOpen={isOpen}
        onOpenChange={setIsOpen}
        title="경고"
        confirm={{
          onClick: async (cancel) => {
            await deleteSpendingLog(id)
            cancel()
            router.replace("/spending/logs")
          },
        }}
        cancel={{ onClick: async (cancel) => cancel() }}
      >
        정말 삭제 하시겠습니까?
      </ConfirmModal>
    </>
  )
}
