"use client"

import { CardUsage, CardUsagesFilter, getCardUsagesPage } from "@/api/spending/card"
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
import CardUsageStatusChip, { isPositive } from "./CardUsageStatusChip"

const columnHelper = createColumnHelper<CardUsage>()

const columns = [
  columnHelper.accessor("id", { header: "ID" }),
  columnHelper.accessor("approvalNumber", {
    header: "승인번호",
    cell: (context) => (
      <Link underline="hover" href={`/spending/card/form?id=${context.row.getValue("id")}`}>
        {context.getValue()}
      </Link>
    ),
  }),
  columnHelper.accessor("merchant", { header: "가맹점" }),
  columnHelper.accessor("status", { header: "상태", cell: ({ getValue }) => <CardUsageStatusChip status={getValue()} /> }),
  columnHelper.accessor("log.amount", {
    id: "amount",
    header: "금액",
    cell: ({ getValue, row }) => <Money amount={getValue()} positive={isPositive(row.original.status)} />,
  }),
  columnHelper.accessor(({ log }) => formatDate(log.time), { id: "date", header: "날짜" }),
  columnHelper.accessor(({ log }) => formatTime(log.time), { id: "time", header: "시간" }),
  columnHelper.accessor("file.card.user.name", { header: "사용자" }),
]

type Props = { pageRequest: PageRequest; page?: Page<CardUsage> }

export default function SpendingCardUsages(props: Props) {
  const [pageRequest, setPageRequest] = useState<PageRequest>(props.pageRequest)
  const [filter, setFilter] = useState<CardUsagesFilter>({})
  const { data: page = props.page, isPending } = useQuery({
    queryKey: ["getCardUsagesPage", filter, pageRequest],
    queryFn: () => getCardUsagesPage(filter, pageRequest),
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
        onClickAddButton={() => router.push("card/form")}
      />
    </Container>
  )
}
