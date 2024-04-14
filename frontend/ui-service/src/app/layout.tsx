import Navigation from "@/components/Navigation"
import { Providers } from "@/components/Providers"
import SystemModal from "@/components/SystemModal"
import "@/styles/globals.css"
import type { Metadata } from "next"

export const metadata: Metadata = {
  title: "Finance Management",
  description: "재무 관리 어플리케이션",
}

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="ko" className="dark">
      <body>
        <Providers>
          <Navigation />
          {children}
          <SystemModal />
        </Providers>
      </body>
    </html>
  )
}
