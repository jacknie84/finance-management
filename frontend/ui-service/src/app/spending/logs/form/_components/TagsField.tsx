import SelectBox from "@/components/Form/SelectBox"
import { distinct, isEmpty } from "@/lib/utils"
import { Chip } from "@nextui-org/react"
import { FieldApi, FormApi } from "@tanstack/react-form"
import { useCallback, useState } from "react"
import { FormModel } from "./types"

type Props = { form: FormApi<FormModel>; preset?: string[] }
type TagsField = FieldApi<FormModel, "tags">

export default function TagsField({ form, preset = [] }: Props) {
  const [value, setValue] = useState<string>("")
  const onEnter = useCallback(
    (field: TagsField) => {
      const tags = field.getValue() ?? []
      const lastTag = tags[tags.length - 1]
      const exception = lastTag ? lastTag.charAt(lastTag.length - 1) === value : false
      if (!exception) {
        field.handleChange(distinct([...tags, value]))
      }
    },
    [value],
  )
  const onSelectionChange = useCallback((field: TagsField, value: string | number) => {
    const tags = field.getValue() ?? []
    if (typeof value === "string") {
      field.handleChange(distinct([...tags, value]))
    }
  }, [])
  const onCloseChip = useCallback((field: TagsField, tag: string) => {
    const tags = field.getValue() ?? []
    field.handleChange(tags.filter((it) => tag !== it))
  }, [])

  return (
    <form.Field name="tags">
      {(field) => (
        <div className="w-full grid grid-cols-3 gap-4">
          <div className="col-span-1">
            <SelectBox
              label="태그"
              value={value}
              items={preset.map((tag) => ({
                key: tag,
                value: tag,
                content: tag,
              }))}
              onInputChange={setValue}
              onSelectionChange={onSelectionChange.bind(null, field)}
              onEnter={() => onEnter(field)}
              isRequired={false}
              isInvalid={!isEmpty(field.state.meta.errors)}
              errorMessage={field.state.meta.errors[0]}
            />
          </div>
          <div className="col-span-2">
            <div className="flex flex-wrap gap-2">
              {field.getValue()?.map((tag) => (
                <Chip key={tag} variant="dot" color="primary" onClose={() => onCloseChip(field, tag)}>
                  {tag}
                  <input type="hidden" name={field.name} value={tag} />
                </Chip>
              ))}
            </div>
          </div>
        </div>
      )}
    </form.Field>
  )
}
