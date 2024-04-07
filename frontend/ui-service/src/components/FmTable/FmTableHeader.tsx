import { TableColumn, TableHeader } from "@nextui-org/react"
import { FmTableColumConfig } from "./types"

type Props = { columns: FmTableColumConfig[] }

export default function FmTableHeader({ columns }: Props) {
  return (
    <TableHeader>
      <TableColumn>Test</TableColumn>
      {/* {columns.map(({ id, name }) => (
        <TableColumn key={id}>{name}</TableColumn>
      ))} */}
    </TableHeader>
  )
}
