import { Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader } from "@nextui-org/react"
import { PropsWithChildren, ReactNode, useCallback } from "react"

type ButtonEvents = { onClick: (cancel: () => void) => Promise<void> }
type ButtonProps = PropsWithChildren & ButtonEvents
type Events = { onOpenChange: (isOpen: boolean) => void }
type Props = { isOpen: boolean; title: ReactNode; confirm: ButtonProps; cancel: ButtonProps } & PropsWithChildren & Events

export default function ConfirmModal({ isOpen, title, children, onOpenChange, confirm, cancel }: Props) {
  const onClickCancel = useCallback(async (close: () => void) => await cancel.onClick(close), [cancel])
  const onClickConfirm = useCallback(async (close: () => void) => await confirm.onClick(close), [confirm])

  return (
    <Modal size="xs" isOpen={isOpen} onOpenChange={onOpenChange}>
      <ModalContent>
        {(onClose) => (
          <>
            <ModalHeader className="flex flex-col gap-1">{title}</ModalHeader>
            <ModalBody>{children}</ModalBody>
            <ModalFooter>
              <Button color="default" onPress={() => onClickCancel(onClose)}>
                {cancel.children ?? "취소"}
              </Button>
              <Button color="primary" onPress={() => onClickConfirm(onClose)}>
                {confirm.children ?? "확인"}
              </Button>
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  )
}
