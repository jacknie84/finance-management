"use client"

import Container from "@/components/Container"
import { Tab, Tabs } from "@nextui-org/react"
import { usePathname } from "next/navigation"

const items = [
  { id: "/spending/logs", label: "내역" },
  { id: "/spending/plan", label: "계획" },
  { id: "/spending/statistics", label: "통계" },
]

export default function SpendingLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  const pathname = usePathname()

  return (
    <Container>
      <Tabs selectedKey={pathname} aria-label="Spending Tabs" items={items}>
        {items.map(({ id, label }) => (
          <Tab key={id} href={id} title={label} />
        ))}
      </Tabs>
      {children}
    </Container>
  )
}
