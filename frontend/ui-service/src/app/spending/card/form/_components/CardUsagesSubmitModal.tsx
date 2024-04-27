import { putFile } from "@/api/common/file"
import { SaveCardUsage, createCardUsage, getCardUsagesPage, putCardUsage } from "@/api/spending/card"
import { createCardUsageFile } from "@/api/spending/file"
import Money from "@/components/Money"
import useSystemModal from "@/hooks/system-modal"
import { CardUsageRawData } from "@/lib/parser"
import { Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader, Progress } from "@nextui-org/react"
import { useRouter } from "next/navigation"
import { useCallback, useEffect, useState } from "react"
import CardUsageStatusChip from "../../_components/CardUsageStatusChip"
import { FormModel } from "./type"

type Events = { onClose: () => void }
type Props = { model?: FormModel } & Events

async function findCardUsage(approvalNumber: string) {
  const page = await getCardUsagesPage({ approvalNumber: [approvalNumber] }, {})
  return page?.content?.[0]
}

export default function CardUsagesSubmitModal({ model, onClose }: Props) {
  const [isOpen, setIsOpen] = useState(false)
  const [isPending, setIsPending] = useState(false)
  const [isComplete, setIsComplete] = useState(false)
  const [progress, setProgress] = useState(0)
  const [inProgressCardUsage, setInProgressCardUsage] = useState<CardUsageRawData>()
  const systemModal = useSystemModal()
  const router = useRouter()
  const onClickStart = useCallback(async () => {
    setIsPending(true)
    setIsComplete(false)
    const { cardId, file, fileDescription, usages } = model!
    try {
      const formData = new FormData()
      const policy = "card-usage"
      formData.append("policy", policy)
      formData.append("file", file!!, file?.name)
      const { key: fileKey } = await putFile(formData)
      const usageFileId = await createCardUsageFile(cardId!, { fileKey, policy, description: fileDescription })
      for (let i = 0; i < usages.length; i++) {
        const it = usages[i]
        const { approvalNumber, merchant, status, amount, time } = it
        setInProgressCardUsage(it)
        const usage = await findCardUsage(approvalNumber)
        const requestBody = {
          approvalNumber,
          merchant,
          status,
          amount,
          time: {
            instant: time.toJSON(),
            zone: "Asia/Seoul",
          },
          fileId: usageFileId,
        } as SaveCardUsage
        if (usage) {
          await putCardUsage(cardId!, usage.id, requestBody)
        } else {
          await createCardUsage(cardId!, requestBody)
        }
        setProgress(((i + 1) / usages.length) * 100)
      }
    } catch (e: any) {
      systemModal.serverActionError(e?.message ?? e)
    } finally {
      setIsPending(false)
      setIsComplete(true)
    }
  }, [model, systemModal])

  useEffect(() => {
    setProgress(0)
    setIsPending(false)
    setIsComplete(false)
    if (model) {
      setIsOpen(true)
    }
  }, [model])

  return (
    <Modal isOpen={isOpen} onOpenChange={setIsOpen} onClose={onClose} isDismissable={false} hideCloseButton={false}>
      <ModalContent>
        {(onClose) => (
          <>
            <ModalHeader>카드 사용 내역 저장</ModalHeader>
            <ModalBody>
              <Progress aria-label="Submitting..." size="md" value={progress} color="success" showValueLabel={true} className="max-w-md" />
              {inProgressCardUsage && (
                <>
                  <div className="flex flex-wrap gap-4">
                    <CardUsageStatusChip status={inProgressCardUsage.status} />
                    <Money amount={inProgressCardUsage.amount} />
                  </div>
                  <div>{inProgressCardUsage.merchant}</div>
                </>
              )}
            </ModalBody>
            <ModalFooter>
              {!isPending && !isComplete ? (
                <>
                  <Button color="default" onClick={onClose}>
                    취소
                  </Button>
                  <Button color="primary" onClick={onClickStart} isDisabled={(model?.usages?.length ?? 0) <= 0}>
                    시작
                  </Button>
                </>
              ) : (
                isComplete && (
                  <Button color="success" onClick={() => router.replace("/spending/card")}>
                    완료
                  </Button>
                )
              )}
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  )
}
