"use client"

import { PageRequest } from "@/api/types"
import { Button, Input, Pagination, Spinner, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import { ColumnDef, flexRender, getCoreRowModel, useReactTable } from "@tanstack/react-table"
import { MouseEventHandler, useState } from "react"

type Events = {
  onSearchValueChange: (value: string) => void
  onPageRequest: (pageRequest: PageRequest) => void
  onClickAddButton: MouseEventHandler<HTMLButtonElement>
}
type PageContent<T> = { items: T[]; total: number }
type Props<T> = { columns: ColumnDef<T, any>[]; isLoading?: boolean; page?: number; total?: number; content?: PageContent<T> } & Events

export default function SearchTable<T>({
  page = 0,
  total = 0,
  content = { items: [], total: 0 },
  isLoading = false,
  columns,
  onSearchValueChange,
  onPageRequest,
  onClickAddButton,
}: Props<T>) {
  const table = useReactTable({ columns, data: content.items, getCoreRowModel: getCoreRowModel() })
  const headers = table.getHeaderGroups()[0].headers
  const rows = table.getRowModel().rows
  const [searchValue, setSearchValue] = useState<string>("")

  const search = (
    <Input
      isClearable
      className="w-full sm:max-w-[44%]"
      placeholder="검색어를 입력해 주세요"
      startContent={<SearchIcon />}
      value={searchValue}
      onValueChange={(value) => {
        setSearchValue(value)
        onSearchValueChange(value)
      }}
    />
  )

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

  const addButton = (
    <Button color="primary" endContent={<PlusIcon />} onClick={onClickAddButton}>
      추가
    </Button>
  )

  const topContent = (
    <div className="flex flex-col gap-4">
      <div className="flex justify-between gap-3 items-end">
        {search}
        {addButton}
      </div>
      {sizing}
    </div>
  )

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

const SearchIcon = () => (
  <svg aria-hidden="true" fill="none" focusable="false" height="1em" role="presentation" viewBox="0 0 24 24" width="1em">
    <path
      d="M11.5 21C16.7467 21 21 16.7467 21 11.5C21 6.25329 16.7467 2 11.5 2C6.25329 2 2 6.25329 2 11.5C2 16.7467 6.25329 21 11.5 21Z"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
    />
    <path d="M22 22L20 20" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" />
  </svg>
)

const PlusIcon = () => (
  <svg aria-hidden="true" fill="none" focusable="false" height={24} role="presentation" viewBox="0 0 24 24" width={24}>
    <g fill="none" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5}>
      <path d="M6 12h12" />
      <path d="M12 18V6" />
    </g>
  </svg>
)
