import { CardIssuer } from "@/api/common/card"
import SelectBox from "@/components/Form/SelectBox"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel> }

export default function CardIssuerField({ form }: Props) {
  return (
    <form.Field
      name="issuer"
      validators={{
        onChange: ({ value }) => isEmpty(value) && "카드 발급사를 선택해 주세요",
      }}
    >
      {(field) => (
        <SelectBox
          label="카드 발급사"
          name={field.name}
          value={field.state.value}
          items={[
            {
              key: "KB",
              value: "KB",
              content: "KB",
            },
          ]}
          onBlur={field.handleBlur}
          onInputChange={(value) => field.handleChange(value as CardIssuer)}
          isRequired
          isInvalid={!isEmpty(field.state.meta.errors)}
          errorMessage={field.state.meta.errors[0]}
        />
      )}
    </form.Field>
  )
}
