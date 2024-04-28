import SingleFileInput from "@/components/Form/SingleFileInput"
import { isEmpty } from "@/lib/utils"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel> }

export default function CardUsageFileField({ form }: Props) {
  return (
    <form.Field
      name="file"
      validators={{
        onChange: ({ value }) => !value && "카드 내역 파일을 선택해 주세요",
        onSubmit: ({ value }) => !value && "카드 내역 파일을 선택해 주세요",
      }}
    >
      {(field) => (
        <>
          <SingleFileInput
            label="카드 내역 파일"
            value={field.state.value}
            accept=".xls,.xlsx"
            isRequired
            onValueChange={(value) => field.handleChange(value)}
            isInvalid={!isEmpty(field.state.meta.errors)}
            errorMessage={field.state.meta.errors[0]}
          />
        </>
      )}
    </form.Field>
  )
}
