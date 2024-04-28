"use client"

import { SpendingLog, getSpendingLogsPage } from "@/api/spending/log"
import { SpendingLogTagPreset } from "@/api/spending/tag"
import { Page, PageRequest } from "@/api/types"
import Container from "@/components/Container"
import PeriodSearchForm from "@/components/Form/PeriodSearchForm"
import SearchInput from "@/components/Form/SearchInput"
import Money from "@/components/Money"
import PagingTable from "@/components/PagingTable"
import DeleteIcon from "@/components/icons/DeleteIcon"
import EyeIcon from "@/components/icons/EyeIcon"
import { isEmpty } from "@/lib/utils"
import { Period } from "@/types"
import { Button, Card, CardBody, CardFooter, Checkbox, Tooltip } from "@nextui-org/react"
import { useQuery } from "@tanstack/react-query"
import { createColumnHelper } from "@tanstack/react-table"
import { useCallback, useMemo, useState } from "react"
import SpendingTagChips from "../../_components/SpendingTagChips"
import SpendingTagSelectBox from "../../_components/SpendingTagSelectBox"
import { addSpendingLogTag, emptySpendingLogTag, removeSpendingLogTag } from "../actions"

type Props = { pageRequest: PageRequest; page?: Page<SpendingLog>; preset?: SpendingLogTagPreset }

const columnHelper = createColumnHelper<SpendingLog>()

export default function SpendingTags(props: Props) {
  const [pageRequest, setPageRequest] = useState<PageRequest>(props.pageRequest)
  const [period, setPeriod] = useState<Period>({})
  const [search, setSearch] = useState<string>()
  const [isEmptyTags, setIsEmptyTags] = useState(false)
  const [selectedLogs, setSelectedLogs] = useState<SpendingLog[]>([])
  const {
    data: page = props.page,
    isPending,
    refetch,
  } = useQuery({
    queryKey: ["getSpendingLogsPage", search, period, isEmptyTags, pageRequest],
    queryFn: () =>
      getSpendingLogsPage({ search001: search ? [search] : [], ...period, conditions: [{ items: isEmptyTags ? ["EMPTY_TAGS"] : [] }] }, pageRequest),
  })
  const columns = useMemo(
    () => [
      columnHelper.accessor("summary", { header: "지출 내용" }),
      columnHelper.accessor("amount", {
        header: "금액",
        cell: ({ getValue }) => <Money amount={getValue()} />,
      }),
      columnHelper.accessor("tags", {
        header: "태그",
        cell: ({ getValue, row }) => (
          <SpendingTagChips
            items={getValue()?.map((tag) => ({ key: tag, value: tag }))}
            onCloseChip={async (tag) => {
              const log = row.original
              if (!isEmpty(log.tags) && log.tags?.includes(tag)) {
                await removeSpendingLogTag(log, tag)
                await refetch()
              }
            }}
          />
        ),
      }),
      columnHelper.display({
        id: "details",
        header: "상세",
        cell: ({ row }) => (
          <Tooltip content={<pre>{JSON.stringify(row.original, null, 2)}</pre>}>
            <span className="text-lg text-default-400 cursor-pointer active:opacity-50">
              <EyeIcon />
            </span>
          </Tooltip>
        ),
      }),
    ],
    [refetch],
  )
  const onTagAdd = useCallback(
    async (tag: string) => {
      if (!isEmpty(selectedLogs)) {
        const promises = selectedLogs.filter(({ tags }) => isEmpty(tags) || !tags?.includes(tag)).map((log) => addSpendingLogTag(log, tag))
        await Promise.all(promises)
        const { data: page } = await refetch()
        const logIds = selectedLogs.map(({ id }) => id)
        setSelectedLogs(page?.content?.filter(({ id }) => logIds.includes(id)) ?? [])
      }
    },
    [selectedLogs, refetch],
  )
  const onClickDeleteAll = useCallback(async () => {
    if (!isEmpty(selectedLogs)) {
      const promises = selectedLogs.filter(({ tags }) => !isEmpty(tags)).map((log) => emptySpendingLogTag(log))
      await Promise.all(promises)
      const { data: page } = await refetch()
      const logIds = selectedLogs.map(({ id }) => id)
      setSelectedLogs(page?.content?.filter(({ id }) => logIds.includes(id)) ?? [])
    }
  }, [selectedLogs, refetch])
  const onSearchChange = useCallback(
    (search: string) => {
      setPageRequest({ ...pageRequest, page: 0 })
      setSearch(search)
    },
    [pageRequest],
  )

  return (
    <Container>
      <div className="flex flex-col gap-4">
        <Card>
          <CardBody>
            <div className="flex flex-col gap-4">
              <PeriodSearchForm onChange={setPeriod} />
              <SearchInput onValueChange={onSearchChange} />
              <Checkbox isSelected={isEmptyTags} onValueChange={setIsEmptyTags}>
                태그 없는 목록
              </Checkbox>
            </div>
          </CardBody>
        </Card>
        <Card>
          <CardBody>
            <SpendingTagSelectBox
              preset={props.preset?.tags}
              onTagAdd={onTagAdd}
              isInvalid={isEmpty(selectedLogs)}
              errorMessage={isEmpty(selectedLogs) && "지출 내역을 선택해 주세요."}
            />
          </CardBody>
          <CardFooter>
            <Button color="danger" endContent={<DeleteIcon />} onClick={onClickDeleteAll}>
              태그 전부 삭제
            </Button>
          </CardFooter>
        </Card>
        <PagingTable
          columns={columns}
          isLoading={isPending}
          page={page?.pageable.pageNumber}
          total={page?.totalPages}
          content={{ items: page?.content ?? [], total: page?.totalElements ?? 0 }}
          onPageRequest={(pageRequest) => setPageRequest({ ...props.pageRequest, ...pageRequest })}
          onSelectionChange={setSelectedLogs}
        />
      </div>
    </Container>
  )
}
