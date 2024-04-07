import { isEmpty } from "@/lib/utils"
import { Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import FmAddButton from "./FmAddButton"
import FmPagination from "./FmPagination"
import FmTableSearch from "./FmTableSearch"
import FmTableSize from "./FmTableSize"
import { FmPaging, FmSizing, FmTableColumConfig, FmTableRowData } from "./types"

type Props = {
  label: string
  search?: string
  paging?: FmPaging
  sizing?: FmSizing
  rows?: FmTableRowData[]
  columns: FmTableColumConfig[]
  onChangeSearch: (search: string) => void
  onChangePage: (page: number) => void
  onChangeSize: (size: number) => void
}

export default function FmTable({ label, search, paging, sizing, rows, columns, onChangeSearch, onChangePage, onChangeSize }: Props) {
  const topContent = (
    <div className="flex flex-col gap-4">
      <div className="flex justify-between gap-3 items-end">
        <FmTableSearch search={search} onChange={onChangeSearch} />
        <FmAddButton />
      </div>
      <FmTableSize sizing={sizing} onChangeSize={onChangeSize} />
    </div>
  )
  const bottomContent = <FmPagination paging={paging} onChange={onChangePage} />

  return (
    <Table removeWrapper aria-label={label} topContent={topContent} bottomContent={bottomContent}>
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
