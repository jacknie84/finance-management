import { isEmpty } from "@/lib/utils"
import { Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import FmPagination from "./FmPagination"
import { FmTableColumConfig, FmTableRowData } from "./types"

type Props = { label: string; rows?: FmTableRowData[]; columns: FmTableColumConfig[] }

export default function FmTable({ label, rows, columns }: Props) {
  return (
    <Table removeWrapper aria-label={label} bottomContent={<FmPagination />}>
      <TableHeader>
        {columns.map(({ id, name }) => (
          <TableColumn key={id}>{name}</TableColumn>
        ))}
      </TableHeader>
      {isEmpty(rows) ? (
        <TableBody emptyContent={"No rows to display."}>{[]}</TableBody>
      ) : (
        <TableBody>
          {rows!!.map((row) => (
            <TableRow key={row.id}>
              {columns.map((column) => (
                <TableCell key={`${row.id}-${column.id}`}>{column.cellValue(row.data)}</TableCell>
              ))}
            </TableRow>
          ))}
        </TableBody>
      )}
    </Table>
  )
}
