"use client"

import { DayOfMonth, SpendingStatistics, Subject, getSpendingStatistics } from "@/api/spending/statistics"
import Container from "@/components/Container"
import PeriodSearchForm from "@/components/Form/PeriodSearchForm"
import SearchInput from "@/components/Form/SearchInput"
import Money from "@/components/Money"
import { formatMoney } from "@/lib/format"
import { isEmpty } from "@/lib/utils"
import { Period } from "@/types"
import { Card, CardBody, Radio, RadioGroup } from "@nextui-org/react"
import { DefaultRawDatum, ResponsivePieCanvas } from "@nivo/pie"
import { useQuery } from "@tanstack/react-query"
import { useMemo, useState } from "react"

const subjects = {
  YEAR: {
    label: "연도별",
    getNameOrder: (name) => name as number,
    getDisplayName: (name) => `${name}`,
  },
  MONTH: {
    label: "월별",
    getNameOrder: (name) => name as number,
    getDisplayName: (name) => `${name}`,
  },
  DAY_OF_MONTH: {
    label: "일별",
    getNameOrder: (name) => name as number,
    getDisplayName: (name) => `${name}`,
  },
  DAY_OF_WEEK: {
    label: "요일별",
    getNameOrder: (name) => dayOfWeeks[name as DayOfMonth].order,
    getDisplayName: (name) => dayOfWeeks[name as DayOfMonth].label,
  },
  HOUR: {
    label: "시간별",
    getNameOrder: (name) => name as number,
    getDisplayName: (name) => `${name}`,
  },
  TAG: {
    label: "태그별",
    getNameOrder: (name) => getTagOrder(name as string),
    getDisplayName: (name) => `${name}`,
  },
  USER: {
    label: "사용자별",
    getNameOrder: (name) => name as number,
    getDisplayName: (name) => `${name}`,
  },
} as Record<Subject, { label: string; getNameOrder: (name: any) => number; getDisplayName: (name: any) => string }>

const dayOfWeeks = {
  SUNDAY: { label: "일요일", order: 0 },
  MONDAY: { label: "월요일", order: 1 },
  TUESDAY: { label: "화요일", order: 2 },
  WEDNESDAY: { label: "수요일", order: 3 },
  THURSDAY: { label: "목요일", order: 4 },
  FRIDAY: { label: "금요일", order: 5 },
  SATURDAY: { label: "토요일", order: 6 },
} as Record<DayOfMonth, { label: string; order: number }>

function getTagOrder(tag: string) {
  let order = 0
  for (let i = 0; i < tag.length; i++) {
    order += tag.charCodeAt(i)
  }
  return order
}

export default function SpendingStatisticsPage() {
  const [period, setPeriod] = useState<Period>({})
  const [search, setSearch] = useState<string>()
  const [subject, setSubject] = useState<Subject>()
  const { data: statistics } = useQuery({
    queryKey: [period, search, subject],
    queryFn: () => (subject ? getSpendingStatistics(subject, { search001: search, ...period }) : {}),
  })
  const data = useMemo<DefaultRawDatum[]>(() => {
    if (isEmpty(statistics)) {
      return []
    }
    const { items } = statistics as SpendingStatistics
    return items.map(({ name, amount }) => ({ id: name, value: amount, label: subjects[subject!].getDisplayName(name) }))
  }, [statistics, subject])
  const totalAmount = useMemo(() => {
    if (isEmpty(statistics)) {
      return 0
    }
    const { totalAmount } = statistics as SpendingStatistics
    return totalAmount
  }, [statistics])

  return (
    <Container>
      <div className="flex flex-col gap-4">
        <Card>
          <CardBody>
            <div className="flex flex-col gap-4">
              <PeriodSearchForm onChange={setPeriod} />
              <SearchInput onValueChange={setSearch} />
              <RadioGroup label="주제 선택" orientation="horizontal" value={subject ?? ""} onValueChange={(value) => setSubject(value as Subject)}>
                {Object.entries(subjects).map(([subject, { label }]) => (
                  <Radio key={subject} value={subject}>
                    {label}
                  </Radio>
                ))}
              </RadioGroup>
            </div>
          </CardBody>
        </Card>
        {statistics && (
          <>
            <div>
              <p>총 금액</p>
              <Money amount={totalAmount} />
            </div>
            <div className="w-full h-96">
              <ResponsivePieCanvas
                data={data}
                valueFormat={formatMoney}
                margin={{ top: 40, right: 80, bottom: 80, left: 80 }}
                innerRadius={0.5}
                padAngle={0.7}
                cornerRadius={3}
                activeOuterRadiusOffset={8}
                borderWidth={1}
                borderColor={{
                  from: "color",
                  modifiers: [["darker", 0.2]],
                }}
                arcLinkLabelsSkipAngle={10}
                arcLinkLabelsTextColor="#FFFFFF"
                arcLinkLabelsThickness={2}
                arcLinkLabelsColor={{ from: "color" }}
                arcLabelsSkipAngle={10}
                arcLabelsTextColor={{
                  from: "color",
                  modifiers: [["darker", 3]],
                }}
                legends={[
                  {
                    anchor: "bottom",
                    direction: "column",
                    justify: false,
                    translateX: 320,
                    translateY: 56,
                    itemsSpacing: 0,
                    itemWidth: 60,
                    itemHeight: 18,
                    itemTextColor: "#FFF",
                    itemDirection: "right-to-left",
                    itemOpacity: 1,
                    symbolSize: 14,
                    symbolShape: "circle",
                    effects: [
                      {
                        on: "hover",
                        style: {
                          itemTextColor: "#000",
                        },
                      },
                    ],
                  },
                ]}
              />
            </div>
          </>
        )}
      </div>
    </Container>
  )
}
