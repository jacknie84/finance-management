import { Period } from "@/types"
import { Button, Card, CardBody } from "@nextui-org/react"
import PlusIcon from "../icons/PlusIcon"
import PeriodSearchForm from "./PeriodSearchForm"
import SearchInput from "./SearchInput"

type Events = { onPeriodChange: (period: Period) => void; onSearchChang: (value: string) => void; onClickAdd: () => void }
type Props = {} & Events

export default function SearchAndAdd({ onPeriodChange, onSearchChang, onClickAdd }: Props) {
  return (
    <Card>
      <CardBody>
        <div className="flex flex-col gap-4">
          <PeriodSearchForm onChange={onPeriodChange} />
          <div className="flex justify-between gap-3 items-end">
            <SearchInput onValueChange={onSearchChang} />
            <Button color="primary" endContent={<PlusIcon />} onClick={onClickAdd}>
              추가
            </Button>
          </div>
        </div>
      </CardBody>
    </Card>
  )
}
