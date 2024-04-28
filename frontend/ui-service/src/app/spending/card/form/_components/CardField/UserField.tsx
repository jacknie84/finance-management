import { User as UserType } from "@/api/common/user"
import SelectBox from "@/components/Form/SelectBox"
import { isEmpty } from "@/lib/utils"
import { User } from "@nextui-org/react"
import { FormApi } from "@tanstack/react-form"
import { FormModel } from "./type"

type Props = { form: FormApi<FormModel>; users: UserType[] }

export default function UserField({ form, users }: Props) {
  return (
    <form.Field
      name="username"
      validators={{
        onChange: ({ value }) => isEmpty(value) && "사용자를 선택해 주세요",
      }}
    >
      {(field) => (
        <SelectBox
          label="사용자"
          name={field.name}
          value={field.state.value}
          items={users.map((user) => ({
            key: user.id,
            value: user.name,
            content: <User name={user.name} description={user.displayName ?? user.name} avatarProps={{ name: user.name }} />,
          }))}
          onBlur={field.handleBlur}
          onInputChange={(value) => field.handleChange(String(value))}
          isRequired
          isInvalid={!isEmpty(field.state.meta.errors)}
          errorMessage={field.state.meta.errors[0]}
        />
      )}
    </form.Field>
  )
}
