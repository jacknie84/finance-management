import { Button, Input } from "@nextui-org/react"
import { useRef } from "react"

type Events = { onValueChange?: (value?: File) => void }
type Props = { value?: File; name?: string; label: string; accept?: string; isRequired?: boolean; isInvalid?: boolean; errorMessage?: any } & Events

export default function SingleFileInput({ value, name, label, accept, isRequired, isInvalid, errorMessage, onValueChange = () => {} }: Props) {
  const fileInputRef = useRef<HTMLInputElement>(null)

  return (
    <>
      <input type="file" ref={fileInputRef} className="hidden" accept={accept} onChange={(e) => onValueChange(e.currentTarget.files?.[0])} />
      <Input
        variant="bordered"
        label={label}
        name={name}
        value={value?.name ?? ""}
        readOnly={true}
        endContent={
          <Button variant="light" color="primary" onClick={() => fileInputRef?.current?.click()}>
            파일선택
          </Button>
        }
        isRequired={isRequired}
        isInvalid={isInvalid}
        errorMessage={errorMessage}
      />
    </>
  )
}
