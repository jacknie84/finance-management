import { CardUsageStatus } from "@/api/spending/card"
import moment from "moment"
import * as XLSX from "xlsx"

export type CardUsageRawData = {
  approvalNumber: string
  merchant: string
  status: CardUsageStatus
  amount: number
  time: Date
}

export async function parseKbExcelFile(file: File) {
  const reader = new FileReader()
  return new Promise<CardUsageRawData[]>((resolve) => {
    reader.onload = (e) => {
      const workbook = XLSX.read(e.target?.result)
      const sheetName = workbook.SheetNames[0]
      const sheet = workbook.Sheets[sheetName]
      const [header, ...rows] = XLSX.utils.sheet_to_json<any>(sheet, { header: 1, range: 6 })
      const mapper = new KbExcelRawDataMapper(header)
      const rawData = rows.map(
        (row) =>
          ({
            approvalNumber: mapper.approvalNumber(row),
            merchant: mapper.merchant(row),
            status: mapper.status(row),
            amount: mapper.amount(row),
            time: mapper.time(row),
          }) as CardUsageRawData,
      )
      resolve(rawData)
    }
    reader.readAsArrayBuffer(file)
  })
}

interface CardUsageRawDataMapper {
  approvalNumber(row: any[]): string
  merchant(row: any[]): string
  status(row: any[]): CardUsageStatus
  amount(row: any[]): number
  time(row: any[]): Date
}

type CardUsageIndices = {
  approvalNumber: number
  merchant: number
  status: number
  amount: number
  date: number
  time: number
}

class KbExcelRawDataMapper implements CardUsageRawDataMapper {
  private indices: CardUsageIndices

  constructor(header: string[]) {
    const removeSpace = (it: string) => it.replaceAll(/\s+/g, "")
    this.indices = {
      date: header.findIndex((it) => it === "이용일"),
      time: header.findIndex((it) => removeSpace(it) === "이용시간"),
      merchant: header.findIndex((it) => it === "이용하신곳"),
      amount: header.findIndex((it) => removeSpace(it) === "국내이용금액(원)"),
      status: header.findIndex((it) => it === "상태"),
      approvalNumber: header.findIndex((it) => it === "승인번호"),
    }
  }

  approvalNumber(row: any[]): string {
    return row[this.indices.approvalNumber] as string
  }

  merchant(row: any[]): string {
    const merchant = row[this.indices.merchant] ?? "알 수 없음"
    return merchant as string
  }

  status(row: any[]): CardUsageStatus {
    const status = row[this.indices.status]
    switch (status) {
      case "전표매입":
        return "INVOICED"
      case "전표미매입":
        return "NOT_INVOICED"
      case "취소전표매입":
        return "CANCELLED_INVOICED"
      case "승인취소":
        return "APPROVAL_CANCELLED"
      default:
        throw new Error(`국민 카드 사용 내역 분석 중 예상 되지 않는 상태(${status})`)
    }
  }

  amount(row: any[]): number {
    return row[this.indices.amount] as number
  }

  time(row: any[]): Date {
    const date = row[this.indices.date] as string
    const time = row[this.indices.time] as string
    return moment(`${date}T${time}`).toDate()
  }
}
