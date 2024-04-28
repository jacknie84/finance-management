import Money from "@/components/Money"
import { formatDate, formatTime } from "@/lib/format"
import { CardUsageRawData, parseKbExcelFile } from "@/lib/parser"
import { ScrollShadow, Spinner, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow } from "@nextui-org/react"
import { FormApi } from "@tanstack/react-form"
import { createColumnHelper, flexRender, getCoreRowModel, useReactTable } from "@tanstack/react-table"
import { useEffect, useState } from "react"
import CardUsageStatusChip, { isPositive } from "../../_components/CardUsageStatusChip"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel> }

const columnHelper = createColumnHelper<CardUsageRawData>()
const columns = [
  columnHelper.accessor("approvalNumber", { header: "승인번호" }),
  columnHelper.accessor("merchant", { header: "가맹점 이름" }),
  columnHelper.accessor("amount", {
    header: "금액",
    cell: ({ getValue, row }) => <Money amount={getValue()} positive={isPositive(row.original.status)} />,
  }),
  columnHelper.accessor("status", { header: "상태", cell: ({ getValue }) => <CardUsageStatusChip status={getValue()} /> }),
  columnHelper.accessor(({ time }) => formatDate(time), { header: "날짜", id: "date" }),
  columnHelper.accessor(({ time }) => formatTime(time), { header: "시간", id: "time" }),
]

export default function CardUsagesField({ form }: Props) {
  const fileField = form.useField({ name: "file" })
  const usagesField = form.useField({ name: "usages" })
  const [isLoading, setIsLoading] = useState(false)

  useEffect(() => {
    usagesField.handleChange([])
    if (fileField.state.value) {
      setIsLoading(true)
      parseKbExcelFile(fileField.state.value)
        .then(usagesField.handleChange)
        .finally(() => setIsLoading(false))
    }
  }, [fileField.state.value, usagesField])

  return (
    <form.Field name="usages">
      {(field) => (
        <ScrollShadow hideScrollBar className="w-full h-[400px]">
          <CardUsagesTable data={field.getValue()} isLoading={isLoading} />
        </ScrollShadow>
      )}
    </form.Field>
  )
}

function CardUsagesTable({ data = [], isLoading }: { data: CardUsageRawData[]; isLoading: boolean }) {
  const table = useReactTable({ columns, data, getCoreRowModel: getCoreRowModel() })
  const headers = table.getHeaderGroups()[0].headers
  const rows = table.getRowModel().rows

  return (
    <Table aria-label="Card Usages Table">
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
