import DateInput from "@/components/Form/DateInput"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./types"

type Props = { form: FormApi<FormModel> }

export default function DateField({ form }: Props) {
  return (
    <form.Field
      name="date"
      validators={{
        onChange: ({ value }) => isEmpty(value) && "날짜를 입력해 주세요",
      }}
    >
      {(field) => (
        <DateInput
          label="날짜"
          name={field.name}
          value={field.state.value}
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
