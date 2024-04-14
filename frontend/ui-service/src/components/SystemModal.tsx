"use client"

import { SystemModalContext } from "@/contexts"
import { Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader } from "@nextui-org/react"
import { useContext } from "react"

export default function SystemModal() {
  const { state = { isOpen: false }, setState } = useContext(SystemModalContext)
  const { isOpen, title, body } = state

  return (
    <Modal size="xs" isOpen={isOpen} onOpenChange={(isOpen) => setState({ ...state, isOpen })}>
      <ModalContent>
        {(onClose) => (
          <>
            <ModalHeader className="flex flex-col gap-1">{title}</ModalHeader>
            <ModalBody>{body}</ModalBody>
            <ModalFooter>
              <Button color="default" onPress={onClose}>
                확인
              </Button>
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  )
}
