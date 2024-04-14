import { Input } from "@nextui-org/react"
import { FocusEvent } from "react"

type Events = { onBlur: (e: FocusEvent<Element>) => void; onValueChange: (value: number) => void }
type Props = {
  value?: number
  name?: string
  defaultValue: number
  label: string
  converter: {
    format: (value?: number) => string | undefined
    parse: (value?: string) => number | undefined | null
  }
  isRequired: boolean
  isInvalid: boolean
  errorMessage?: any
} & Events

export default function NumberInput({
  value,
  defaultValue,
  name,
  label,
  converter,
  isRequired,
  isInvalid,
  errorMessage,
  onBlur,
  onValueChange,
}: Props) {
  return (
    <Input
      variant="bordered"
      name={name}
      label={label}
      value={converter.format(value ?? defaultValue)}
      onBlur={onBlur}
      onValueChange={(value) => onValueChange(value ? converter.parse(value)!! : defaultValue)}
      isClearable
      isRequired={isRequired}
      isInvalid={isInvalid}
      errorMessage={errorMessage}
    />
  )
}
