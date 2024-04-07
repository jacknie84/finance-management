"use client"

import { NextUIProvider } from "@nextui-org/react"
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"

type Props = { children: React.ReactNode }

const queryClient = new QueryClient()

export function Providers({ children }: Props) {
  return (
    <NextUIProvider>
      <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
    </NextUIProvider>
  )
}
