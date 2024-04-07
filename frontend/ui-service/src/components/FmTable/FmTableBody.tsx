import { TableBody, TableCell, TableRow } from "@nextui-org/react"
import { FmTableColumConfig, FmTableRowData } from "./types"

type Props = {
  rows?: FmTableRowData[]
  columns: FmTableColumConfig[]
}

export default function FmTableBody({ rows, columns }: Props) {
  return rows && rows!!.length > 0 ? (
    <TableBody>
      {rows!!.map((row) => (
        <TableRow key={row.id}>
          {columns.map((column) => (
            <TableCell key={`${row.id}-${column.id}`}>{column.cellValue(row.data)}</TableCell>
          ))}
        </TableRow>
      ))}
    </TableBody>
  ) : (
    <TableBody emptyContent={"No rows to display."}>{[]}</TableBody>
  )
}
