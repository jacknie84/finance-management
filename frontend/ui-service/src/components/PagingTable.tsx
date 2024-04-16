"use client"

import { PageRequest } from "@/api/types"
import { Pagination, Spinner, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import { ColumnDef, flexRender, getCoreRowModel, useReactTable } from "@tanstack/react-table"

type Events = {
  onPageRequest: (pageRequest: PageRequest) => void
}
type PageContent<T> = { items: T[]; total: number }
type Props<T> = { columns: ColumnDef<T, any>[]; isLoading?: boolean; page?: number; total?: number; content?: PageContent<T> } & Events

export default function PagingTable<T>({
  page = 0,
  total = 0,
  content = { items: [], total: 0 },
  isLoading = false,
  columns,
  onPageRequest,
}: Props<T>) {
  const table = useReactTable({ columns, data: content.items, getCoreRowModel: getCoreRowModel() })
  const headers = table.getHeaderGroups()[0].headers
  const rows = table.getRowModel().rows

  const sizing = (
    <div className="flex justify-between items-center">
      <span className="text-default-400 text-small">Total {content.total} rows</span>
      <label className="flex items-center text-default-400 text-small">
        Rows per page:
        <select
          className="bg-transparent outline-none text-default-400 text-small"
          defaultValue={10}
          onChange={(e) => onPageRequest({ size: Number(e.target.value) })}
        >
          <option value={10}>10</option>
          <option value={20}>20</option>
          <option value={50}>50</option>
        </select>
      </label>
    </div>
  )

  const topContent = <div className="flex flex-col gap-4">{sizing}</div>

  const pagination = (
    <div className="flex w-full justify-center">
      <Pagination
        total={total}
        initialPage={1}
        page={page + 1}
        variant="bordered"
        onChange={(page) => onPageRequest({ page: page - 1 })}
        showControls
        showShadow
        color="secondary"
      />
    </div>
  )

  return (
    <Table aria-label="Search Table" topContent={topContent} bottomContent={!isLoading && total > 0 && pagination}>
      <TableHeader columns={headers}>
        {(header) => <TableColumn key={header.id}>{flexRender(header.column.columnDef.header, header.getContext())}</TableColumn>}
      </TableHeader>
      <TableBody items={rows} isLoading={isLoading} loadingContent={<Spinner />} emptyContent={"No rows to display."}>
        {(row) => (
          <TableRow key={row.id}>
            {row.getVisibleCells().map((cell) => (
              <TableCell key={cell.id}>{flexRender(cell.column.columnDef.cell, cell.getContext())}</TableCell>
            ))}
          </TableRow>
        )}
      </TableBody>
    </Table>
  )
}
