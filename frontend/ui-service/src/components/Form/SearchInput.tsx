import { debounce } from "@/lib/utils"
import { Input } from "@nextui-org/react"
import { useMemo, useState } from "react"
import SearchIcon from "../icons/SearchIcon"

type Events = { onValueChange: (value: string) => void }
type Props = { wait?: number } & Events

export default function SearchInput({ wait = 300, ...props }: Props) {
  const [searchValue, setSearchValue] = useState("")
  const onValueChange = useMemo(() => debounce((value: string) => props.onValueChange(value), wait), [wait, props])

  return (
    <Input
      isClearable
      variant="bordered"
      className="w-full sm:max-w-[44%]"
      placeholder="검색어를 입력해 주세요"
      startContent={<SearchIcon />}
      value={searchValue}
      onValueChange={(value) => {
        setSearchValue(value)
        onValueChange(value)
      }}
    />
  )
}
