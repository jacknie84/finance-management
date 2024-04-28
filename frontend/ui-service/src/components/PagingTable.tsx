"use client"

import { PageRequest } from "@/api/types"
import { Pagination, Selection, Spinner, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import { ColumnDef, flexRender, getCoreRowModel, useReactTable } from "@tanstack/react-table"
import { useCallback, useState } from "react"

type Events<T> = {
  onPageRequest: (pageRequest: PageRequest) => void
  onSelectionChange?: (selection: T[]) => void
}
type PageContent<T> = { items: T[]; total: number }
type Props<T> = { columns: ColumnDef<T, any>[]; isLoading?: boolean; page?: number; total?: number; content?: PageContent<T> } & Events<T>

export default function PagingTable<T>({
  page = 0,
  total = 0,
  content = { items: [], total: 0 },
  isLoading = false,
  columns,
  onPageRequest,
  ...props
}: Props<T>) {
  const table = useReactTable({ columns, data: content.items, getCoreRowModel: getCoreRowModel() })
  const [pageSize, setPageSize] = useState(10)
  const headers = table.getHeaderGroups()[0].headers
  const [selectedKeys, setSelectedKeys] = useState<Selection>(new Set())
  const onSelectionChange = useCallback(
    (selection: Selection) => {
      setSelectedKeys(selection)
      const { onSelectionChange = () => {} } = props
      if (selection === "all") {
        const { rows } = table.getRowModel()
        const items = rows.map(({ original }) => original)
        onSelectionChange(items)
      } else {
        const items = [] as T[]
        selection.forEach((key) => {
          const { original } = table.getRow(key as string)
          items.push(original)
        })
        onSelectionChange(items)
      }
    },
    [props, table],
  )
  const onPageSizeChange = useCallback(
    (size: number) => {
      setPageSize(size)
      onPageRequest({ size })
      setSelectedKeys(new Set())
    },
    [onPageRequest],
  )

  const sizing = (
    <div className="flex justify-between items-center">
      <span className="text-default-400 text-small">Total {content.total} rows</span>
      <label className="flex items-center text-default-400 text-small">
        Rows per page:
        <select
          className="bg-transparent outline-none text-default-400 text-small"
          value={pageSize}
          onChange={(e) => onPageSizeChange(Number(e.target.value))}
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
        onChange={(page) => onPageRequest({ page: page - 1, size: pageSize })}
        showControls
        showShadow
        color="secondary"
      />
    </div>
  )

  return (
    <Table
      aria-label="Paging Table"
      topContent={topContent}
      bottomContent={!isLoading && total > 0 && pagination}
      selectionMode={props.onSelectionChange && "multiple"}
      selectedKeys={selectedKeys}
      onSelectionChange={onSelectionChange}
    >
      <TableHeader columns={headers}>
        {(header) => <TableColumn key={header.id}>{flexRender(header.column.columnDef.header, header.getContext())}</TableColumn>}
      </TableHeader>
      <TableBody items={table.getRowModel().rows} isLoading={isLoading} loadingContent={<Spinner />} emptyContent={"No rows to display."}>
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
