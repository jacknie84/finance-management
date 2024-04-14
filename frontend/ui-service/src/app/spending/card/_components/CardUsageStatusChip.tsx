import { CardUsageStatus } from "@/api/spending/card"
import { Chip } from "@nextui-org/react"

type Props = { status: CardUsageStatus }

function getChipColor(status: CardUsageStatus) {
  return isPositive(status) ? "success" : "warning"
}

export function isPositive(status: CardUsageStatus) {
  switch (status) {
    case "NOT_INVOICED":
    case "INVOICED":
      return true
    case "CANCELLED_INVOICED":
    case "APPROVAL_CANCELLED":
      return false
  }
}

function getChipLabel(status: CardUsageStatus) {
  switch (status) {
    case "NOT_INVOICED":
      return "전표미매입"
    case "INVOICED":
      return "전표매입"
    case "CANCELLED_INVOICED":
      return "취소전표매입"
    case "APPROVAL_CANCELLED":
      return "승인취소"
  }
}

export default function CardUsageStatusChip({ status }: Props) {
  return (
    <Chip variant="bordered" color={getChipColor(status)}>
      {getChipLabel(status)}
    </Chip>
  )
}
