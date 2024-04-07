import { Pagination } from "@nextui-org/react"

export default function FmTablePagination() {
  return (
    <div className="flex w-full justify-center">
      <Pagination total={10} initialPage={1} isCompact showControls showShadow color="secondary" />
    </div>
  )
}
