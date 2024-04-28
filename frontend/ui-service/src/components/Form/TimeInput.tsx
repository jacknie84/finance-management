import { Input } from "@nextui-org/react"
import { FocusEvent } from "react"

type Events = { onBlur?: (e: FocusEvent<Element>) => void; onValueChange?: (value: string) => void }
type Props = { value?: string; name?: string; label: string; isRequired?: boolean; isInvalid?: boolean; errorMessage?: any } & Events

export default function TimeInput({ value = "", name, label, isRequired, isInvalid, errorMessage, onBlur, onValueChange }: Props) {
  return (
    <Input
      type="time"
      variant="bordered"
      name={name}
      label={label}
      labelPlacement="outside"
      value={value}
      onBlur={onBlur}
      onValueChange={onValueChange}
      isClearable
      isRequired={isRequired}
      isInvalid={isInvalid}
      errorMessage={errorMessage}
    />
  )
}
