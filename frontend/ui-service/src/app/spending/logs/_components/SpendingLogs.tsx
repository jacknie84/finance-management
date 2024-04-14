"use client"

import { SpendingLog, SpendingLogsFilter, getSpendingLogsPage } from "@/api/spending/log"
import { Page, PageRequest } from "@/api/types"
import Container from "@/components/Container"
import Money from "@/components/Money"
import SearchTable from "@/components/SearchTable"
import { formatDate, formatTime } from "@/lib/format"
import { debounce } from "@/lib/utils"
import { Link } from "@nextui-org/react"
import { useQuery } from "@tanstack/react-query"
import { createColumnHelper } from "@tanstack/react-table"
import { useRouter } from "next/navigation"
import { useState } from "react"

const columnHelper = createColumnHelper<SpendingLog>()

const columns = [
  columnHelper.accessor("id", { header: "ID" }),
  columnHelper.accessor("summary", {
    header: "지출 내용",
    cell: (context) => (
      <Link underline="hover" href={`/spending/logs/form?id=${context.row.getValue("id")}`}>
        {context.getValue()}
      </Link>
    ),
  }),
  columnHelper.accessor("amount", {
    header: "금액",
    cell: ({ getValue }) => <Money amount={getValue()} />,
  }),
  columnHelper.accessor((log) => formatDate(log.time), { id: "date", header: "날짜" }),
  columnHelper.accessor((log) => formatTime(log.time), { id: "time", header: "시간" }),
  columnHelper.accessor("user.name", { header: "사용자" }),
]

type Props = { pageRequest: PageRequest; page?: Page<SpendingLog> }

export default function SpendingLogs(props: Props) {
  const [pageRequest, setPageRequest] = useState<PageRequest>(props.pageRequest)
  const [filter, setFilter] = useState<SpendingLogsFilter>({})
  const { data: page = props.page, isPending } = useQuery({
    queryKey: ["getSpendingLogsPage", filter, pageRequest],
    queryFn: () => getSpendingLogsPage(filter, pageRequest),
  })
  const router = useRouter()

  return (
    <Container>
      <SearchTable
        columns={columns}
        isLoading={isPending}
        page={page?.pageable.pageNumber}
        total={page?.totalPages}
        content={{ items: page?.content ?? [], total: page?.totalElements ?? 0 }}
        onSearchValueChange={debounce((value: string) => setFilter({ search001: value ? [value] : [] }), 300)}
        onPageRequest={(pageRequest) => setPageRequest({ ...props.pageRequest, ...pageRequest })}
        onClickAddButton={() => router.push("logs/form")}
      />
    </Container>
  )
}
