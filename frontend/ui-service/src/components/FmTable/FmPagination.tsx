import { Pagination } from "@nextui-org/react"
import { FmPaging } from "./types"

type Props = { paging?: FmPaging; onChange: (page: number) => void }

export default function FmPagination({ paging = {}, onChange }: Props) {
  const { page = 0, total = 1 } = paging
  return (
    <div className="flex w-full justify-center">
      <Pagination
        total={total}
        initialPage={1}
        page={page + 1}
        variant="bordered"
        onChange={(page) => onChange(page - 1)}
        showControls
        showShadow
        color="secondary"
      />
    </div>
  )
}
