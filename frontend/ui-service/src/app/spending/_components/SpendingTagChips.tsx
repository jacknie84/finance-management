import { Chip } from "@nextui-org/react"
import { ReactNode } from "react"

type Item = { value: ReactNode; key: string }
type Events = { onCloseChip: (key: string) => void }
type Props = { items?: Item[] } & Events

export default function SpendingTagChips({ items = [], onCloseChip }: Props) {
  return (
    <div className="flex flex-wrap gap-2">
      {items.map(({ key, value }) => (
        <Chip key={key} variant="dot" color="primary" onClose={() => onCloseChip(key)}>
          {value}
        </Chip>
      ))}
    </div>
  )
}
