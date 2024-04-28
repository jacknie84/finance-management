import { CardUsageRawData } from "@/lib/parser"

export type FormModel = {
  cardId?: number
  file?: File
  fileDescription?: string
  usages: CardUsageRawData[]
}
