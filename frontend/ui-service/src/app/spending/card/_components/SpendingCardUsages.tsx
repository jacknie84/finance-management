"use client"

import { CardUsage, getCardUsagesPage } from "@/api/spending/card"
import { Page, PageRequest } from "@/api/types"
import Container from "@/components/Container"
import SearchAndAdd from "@/components/Form/SearchAndAdd"
import Money from "@/components/Money"
import PagingTable from "@/components/PagingTable"
import { formatDate, formatTime } from "@/lib/format"
import { Period } from "@/types"
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
  const [period, setPeriod] = useState<Period>({})
  const [search, setSearch] = useState<string>()
  const { data: page = props.page, isPending } = useQuery({
    queryKey: ["getCardUsagesPage", search, period, pageRequest],
    queryFn: () => getCardUsagesPage({ search001: search ? [search] : [], ...period }, pageRequest),
  })
  const router = useRouter()

  return (
    <Container>
      <div className="flex flex-col gap-4">
        <SearchAndAdd onPeriodChange={setPeriod} onSearchChang={setSearch} onClickAdd={() => router.push("card/form")} />
        <PagingTable
          columns={columns}
          isLoading={isPending}
          page={page?.pageable.pageNumber}
          total={page?.totalPages}
          content={{ items: page?.content ?? [], total: page?.totalElements ?? 0 }}
          onPageRequest={(pageRequest) => setPageRequest({ ...props.pageRequest, ...pageRequest })}
        />
      </div>
    </Container>
  )
}
