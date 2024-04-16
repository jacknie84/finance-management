"use client"

import { SpendingLog, getSpendingLogsPage } from "@/api/spending/log"
import { Page, PageRequest } from "@/api/types"
import Container from "@/components/Container"
import PeriodSearchForm from "@/components/Form/PeriodSearchForm"
import SearchInput from "@/components/Form/SearchInput"
import Money from "@/components/Money"
import PagingTable from "@/components/PagingTable"
import PlusIcon from "@/components/icons/PlusIcon"
import { formatDate, formatTime } from "@/lib/format"
import { Button, Card, CardBody, Link } from "@nextui-org/react"
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
type Period = { start?: string; end?: string }

export default function SpendingLogs(props: Props) {
  const [pageRequest, setPageRequest] = useState<PageRequest>(props.pageRequest)
  const [period, setPeriod] = useState<Period>({})
  const [search, setSearch] = useState<string>()
  const { data: page = props.page, isPending } = useQuery({
    queryKey: ["getSpendingLogsPage", search, period, pageRequest],
    queryFn: () => getSpendingLogsPage({ search001: search ? [search] : [], ...period }, pageRequest),
  })
  const router = useRouter()

  return (
    <Container>
      <div className="flex flex-col gap-4">
        <Card>
          <CardBody>
            <div className="flex flex-col gap-4">
              <PeriodSearchForm onChange={setPeriod} />
              <div className="flex justify-between gap-3 items-end">
                <SearchInput onValueChange={setSearch} />
                <Button color="primary" endContent={<PlusIcon />} onClick={() => router.push("logs/form")}>
                  추가
                </Button>
              </div>
            </div>
          </CardBody>
        </Card>
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
