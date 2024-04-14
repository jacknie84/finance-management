"use client"

import { SystemModalContext, SystemModalState } from "@/contexts"
import { NextUIProvider } from "@nextui-org/react"
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"
import { useRouter } from "next/navigation"
import { useState } from "react"

type Props = { children: React.ReactNode }

const queryClient = new QueryClient()

export function Providers({ children }: Props) {
  const router = useRouter()
  const [state, setState] = useState<SystemModalState>({ isOpen: false })

  return (
    <NextUIProvider navigate={router.push}>
      <SystemModalContext.Provider value={{ state, setState }}>
        <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
      </SystemModalContext.Provider>
    </NextUIProvider>
  )
}
