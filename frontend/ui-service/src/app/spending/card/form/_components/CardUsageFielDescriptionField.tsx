import TextInput from "@/components/Form/TextInput"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel> }

export default function CardUsageFileDescriptionField({ form }: Props) {
  return (
    <form.Field name="fileDescription" validators={{ onChange: ({ value }) => (value?.length ?? 0) > 200 && "200자 이내로 입력 가능 합니다" }}>
      {(field) => (
        <>
          <TextInput
            label="카드 내역 파일 설명"
            value={field.state.value}
            onValueChange={(value) => field.handleChange(value)}
            isInvalid={!isEmpty(field.state.meta.errors)}
            errorMessage={field.state.meta.errors[0]}
          />
        </>
      )}
    </form.Field>
  )
}
