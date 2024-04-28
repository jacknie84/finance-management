import { formatMoney } from "@/lib/format"

type Props = { amount: number; positive?: Boolean }

export default function Money({ amount, positive = amount > 0 }: Props) {
  return <p className={`${positive ? "text-blue-500" : "text-red-600"}`}>{formatMoney(amount)}</p>
}
