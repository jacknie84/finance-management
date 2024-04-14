import { CardIssuer } from "@/api/common/card"

export type FormModel = {
  name: string
  number: string
  issuer: CardIssuer
  username: string
}
