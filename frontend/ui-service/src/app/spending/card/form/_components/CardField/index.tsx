import { Card, getCardsPage } from "@/api/common/card"
import SelectBox from "@/components/Form/SelectBox"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { useQuery } from "@tanstack/react-query"
import { useMemo } from "react"
import { FormModel } from "../type"
import CardRegisterButton from "./CardRegisterButton"

type Events = { onSelected: () => void }
type Props = { form: FormApi<FormModel> } & Events

const displayCard = (card?: Card) => {
  if (card) {
    const { name, issuer, number } = card
    return `${name}(${issuer}: ${number})`
  } else {
    return ""
  }
}

const findCard = (value?: string | number, cards?: Record<string | number, Card>) => {
  if (value && cards) {
    return cards[value]
  } else {
    return undefined
  }
}

export default function CardField({ form, onSelected }: Props) {
  const { data: page, refetch } = useQuery({ queryKey: ["getCardsPage"], queryFn: () => getCardsPage() })
  const cards = useMemo(() => {
    const cards = page?.content ?? []
    return cards.reduce((prev, it) => ({ ...prev, [it.id]: it, [it.number]: it }), {})
  }, [page])
  const items = useMemo(() => {
    const cards = page?.content ?? []
    return cards.map((card) => ({ key: card.id, value: displayCard(card), content: displayCard(card) }))
  }, [page])

  return (
    <form.Field name="cardId" validators={{ onChange: ({ value }) => (!value || value <= 0) && "카드를 선택해 주세요" }}>
      {(field) => (
        <>
          <SelectBox
            label="카드 선택"
            items={items}
            isRequired
            value={displayCard(findCard(field.state.value, cards))}
            onSelectionChange={(cardId) => {
              field.handleChange(Number(cardId))
              onSelected()
            }}
            isInvalid={!isEmpty(field.state.meta.errors)}
            errorMessage={field.state.meta.errors[0]}
          />
          <CardRegisterButton onSubmitted={() => refetch()} />
        </>
      )}
    </form.Field>
  )
}
