import TextInput from "@/components/Form/TextInput"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel> }

export default function CardNameField({ form }: Props) {
  return (
    <form.Field
      name="name"
      validators={{
        onChange: ({ value }) => (isEmpty(value) ? "카드 이름을 입력해 주세요" : value.length > 200 && "200자 까지 입력 가능 합니다"),
      }}
    >
      {(field) => (
        <TextInput
          label="카드 이름"
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
