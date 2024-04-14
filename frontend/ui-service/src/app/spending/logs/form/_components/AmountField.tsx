import NumberInput from "@/components/Form/NumberInput"
import { formatMoney, parseMoney } from "@/lib/format"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./types"

type Props = { form: FormApi<FormModel> }

export default function AmountField({ form }: Props) {
  return (
    <form.Field
      name="amount"
      validators={{
        onChange: ({ value }) => value <= 0 && "금액을 입력해 주세요",
      }}
    >
      {(field) => (
        <NumberInput
          label="금액"
          name={field.name}
          value={field.state.value}
          defaultValue={0}
          converter={{
            format: (amount) => formatMoney(amount, ""),
            parse: parseMoney,
          }}
          onBlur={field.handleBlur}
          onValueChange={(value) => field.handleChange(value)}
          isRequired
          isInvalid={!isEmpty(field.state.meta.errors)}
          errorMessage={field.state.meta.errors[0]}
        />
      )}
    </form.Field>
  )
}
