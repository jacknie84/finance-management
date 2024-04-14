import { User } from "@/api/common/user"
import { ReactNode, createContext } from "react"

type SystemModalContextType = {
  state?: SystemModalState
  setState: (state: SystemModalState) => void
}

export type SystemModalState = { isOpen: boolean; title?: ReactNode; body?: ReactNode }
export const SystemModalContext = createContext<SystemModalContextType>({ setState: () => {} })

export type UsersContextType = { users?: User[] }
export const UsersContext = createContext<UsersContextType>({})
