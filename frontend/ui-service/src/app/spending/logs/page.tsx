"use client"

import Container from "@/components/Container"
import FmTable from "@/components/FmTable"
import { FmTableColumConfig, FmTableRowData } from "@/components/FmTable/types"
import { formatDate, formatMoney, formatTime } from "@/lib/format"
import { useCallback, useEffect, useState } from "react"
import { SpendingLog, getSpendingLogsPage } from "./actions"

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

export default function SpendingLogsPage() {
  const [rows, setRows] = useState<FmTableRowData[]>([])

  const loadRows = useCallback(async () => {
    const page = await getSpendingLogsPage()
    const logs = page?.content ?? []
    return logs.map((log) => ({ id: log.id, data: log }))
  }, [])

  useEffect(() => {
    loadRows().then(setRows)
  }, [loadRows])

  return (
    <Container>
      <FmTable label="Spending Logs Table" rows={rows} columns={columns} />
    </Container>
  )
}
