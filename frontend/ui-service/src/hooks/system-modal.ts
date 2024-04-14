import { SystemModalContext } from "@/contexts"
import { useContext, useMemo } from "react"

export default function useSystemModal() {
  const { setState } = useContext(SystemModalContext)
  return useMemo(
    () => ({
      serverActionSuccess: (message?: any) => setState({ isOpen: true, title: "Server Action Success", body: message ?? "성공" }),
      serverActionError: (message?: any) => setState({ isOpen: true, title: "Server Action Error", body: message ?? "시스템 에러 발생" }),
    }),
    [setState],
  )
}
