import { Autocomplete, AutocompleteItem } from "@nextui-org/react"
import { FocusEvent, ReactNode } from "react"

type Events = {
  onBlur?: (e: FocusEvent<Element>) => void
  onInputChange?: (value: string) => void
  onSelectionChange?: (value: string | number) => void
  onEnter?: () => void
}
type Item = { key: string | number; value: string; content: ReactNode }
type Props = {
  value?: string
  name?: string
  label: string
  items?: Iterable<Item>
  isRequired?: boolean
  isInvalid?: boolean
  errorMessage?: any
} & Events

export default function SelectBox({
  value = "",
  name,
  label,
  items = [],
  isRequired,
  isInvalid,
  errorMessage,
  onBlur,
  onInputChange,
  onSelectionChange,
  onEnter,
}: Props) {
  return (
    <Autocomplete
      variant="bordered"
      name={name}
      label={label}
      inputValue={value}
      items={items}
      onBlur={onBlur}
      onInputChange={onInputChange}
      onSelectionChange={onSelectionChange}
      onKeyDown={(e) => {
        if ("continuePropagation" in e) {
          e.continuePropagation()
        }
        e.key === "Enter" && onEnter && onEnter()
      }}
      isClearable
      isRequired={isRequired}
      isInvalid={isInvalid}
      errorMessage={errorMessage}
    >
      {({ key, value, content }) => (
        <AutocompleteItem key={key} textValue={value}>
          {content}
        </AutocompleteItem>
      )}
    </Autocomplete>
  )
}
