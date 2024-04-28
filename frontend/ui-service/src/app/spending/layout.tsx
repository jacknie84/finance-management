"use client"

import Container from "@/components/Container"
import { Tab, Tabs } from "@nextui-org/react"
import Link from "next/link"
import { useSelectedLayoutSegment } from "next/navigation"

const items = [
  { id: "logs", label: "내역" },
  { id: "card", label: "카드" },
  { id: "tags", label: "태그" },
  { id: "plan", label: "계획" },
  { id: "statistics", label: "통계" },
]

export default function SpendingLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  const layoutSegment = useSelectedLayoutSegment()

  return (
    <Container>
      <Tabs selectedKey={layoutSegment} variant="bordered" aria-label="Spending Tabs" items={items}>
        {({ id, label }) => <Tab key={id} title={<Link href={`/spending/${id}`}>{label}</Link>} />}
      </Tabs>
      {children}
    </Container>
  )
}
