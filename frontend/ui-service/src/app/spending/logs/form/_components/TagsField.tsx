import SpendingTagSelectBox from "@/app/spending/_components/SpendingTagSelectBox"
import { distinct, isEmpty } from "@/lib/utils"
import { Chip } from "@nextui-org/react"
import { FieldApi, FormApi } from "@tanstack/react-form"
import { useCallback } from "react"
import { FormModel } from "./types"

type Props = { form: FormApi<FormModel>; preset?: string[] }
type TagsField = FieldApi<FormModel, "tags">

export default function TagsField({ form, preset }: Props) {
  const onCloseChip = useCallback((field: TagsField, tag: string) => {
    const tags = field.getValue() ?? []
    field.handleChange(tags.filter((it) => tag !== it))
  }, [])

  return (
    <form.Field name="tags">
      {(field) => (
        <div className="w-full grid grid-cols-3 gap-4">
          <div className="col-span-1">
            <SpendingTagSelectBox
              preset={preset}
              onTagAdd={(tag) => {
                const tags = field.getValue() ?? []
                field.handleChange(distinct([...tags, tag]))
              }}
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
