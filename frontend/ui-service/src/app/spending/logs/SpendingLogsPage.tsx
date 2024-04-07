"use client"

import { PageRequest } from "@/api/types"
import Container from "@/components/Container"
import FmTable from "@/components/FmTable"
import { FmPaging, FmSizing, FmTableColumConfig } from "@/components/FmTable/types"
import { formatDate, formatMoney, formatTime } from "@/lib/format"
import { debounce } from "@/lib/utils"
import { useQuery } from "@tanstack/react-query"
import { useEffect, useMemo, useState } from "react"
import { SpendingLog, SpendingLogsFilter, getSpendingLogsPage } from "./actions"

const columns: FmTableColumConfig<SpendingLog>[] = [
  {
    id: "id",
    name: "ID",
    cellValue: ({ id }) => id,
  },
  {
    id: "summary",
    name: "요약",
    cellValue: ({ summary }) => summary ?? "-",
  },
  {
    id: "amount",
    name: "금액",
    cellValue: ({ amount }) => formatMoney(amount),
  },
  {
    id: "date",
    name: "날짜",
    cellValue: ({ time: { instant } }) => formatDate(instant),
  },
  {
    id: "time",
    name: "시간",
    cellValue: ({ time: { instant } }) => formatTime(instant),
  },
]

const defaultPageRequest = { page: 0, size: 10, sort: { fields: ["id"], direction: "desc" } } as PageRequest

export default function SpendingLogsPage() {
  const [pageRequest, setPageRequest] = useState<PageRequest>(defaultPageRequest)
  const [filter, setFilter] = useState<SpendingLogsFilter>({})
  const [search, setSearch] = useState<string>()
  const [paging, setPaging] = useState<FmPaging>()
  const [sizing, setSizing] = useState<FmSizing>()
  const { data: page } = useQuery({
    queryKey: ["getSpendingLogsPage", pageRequest, filter],
    queryFn: () => getSpendingLogsPage(filter, pageRequest),
  })
  const rows = useMemo(() => {
    const logs = page?.content ?? []
    return logs.map((log) => ({ id: log.id, data: log }))
  }, [page])

  useEffect(() => {
    setPaging({
      page: page?.pageable.pageNumber,
      total: page?.totalPages,
    })
    setSizing({
      size: page?.size ?? defaultPageRequest.size!!,
      total: page?.totalElements,
    })
  }, [page])

  useEffect(() => {
    debounce((it) => {
      if (it) {
        setFilter({ containsSummary: [it] })
      } else {
        setFilter({})
      }
    }, 300)(search)
  }, [search])

  return (
    <Container>
      <FmTable
        label="Spending Logs Table"
        search={search}
        paging={paging}
        sizing={sizing}
        rows={rows}
        columns={columns}
        onChangeSearch={setSearch}
        onChangePage={(page) => setPageRequest({ ...defaultPageRequest, page })}
        onChangeSize={(size) => setPageRequest({ ...defaultPageRequest, size })}
      />
    </Container>
  )
}
