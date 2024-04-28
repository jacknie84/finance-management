import SelectBox from "@/components/Form/SelectBox"
import { useCallback, useState } from "react"

type Events = { onTagAdd: (tag: string) => void }
type Props = { preset?: string[]; isInvalid?: boolean; errorMessage?: any } & Events

export default function SpendingTagSelectBox({ preset = [], isInvalid, errorMessage, onTagAdd }: Props) {
  const [value, setValue] = useState("")
  const [before, setBefore] = useState("")
  const onEnter = useCallback(() => {
    const exception = before ? before.charAt(before.length - 1) === value : false
    if (!exception) {
      onTagAdd(value)
      setBefore(value)
    }
  }, [value, before, onTagAdd])

  const onSelectionChange = useCallback(
    (value: string | number) => {
      if (typeof value === "string") {
        onTagAdd(value)
      }
    },
    [onTagAdd],
  )

  return (
    <SelectBox
      label="태그"
      value={value}
      items={preset.map((tag) => ({
        key: tag,
        value: tag,
        content: tag,
      }))}
      onInputChange={setValue}
      onSelectionChange={onSelectionChange}
      onEnter={onEnter}
      isRequired={false}
      isInvalid={isInvalid}
      errorMessage={errorMessage}
    />
  )
}
