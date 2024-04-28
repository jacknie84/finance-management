import TimeInput from "@/components/Form/TimeInput"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./types"

type Props = { form: FormApi<FormModel> }

export default function TimeField({ form }: Props) {
  return (
    <form.Field
      name="time"
      validators={{
        onChange: ({ value }) => isEmpty(value) && "시간을 입력해 주세요",
      }}
    >
      {(field) => (
        <TimeInput
          label="시간"
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
