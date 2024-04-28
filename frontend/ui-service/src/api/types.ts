export type Page<T> = {
  content: T[]
  pageable: { pageNumber: number }
  totalPages: number
  totalElements: number
  size: number
}

export type PageRequest = {
  page?: number
  size?: number
  sort?: { fields: string[]; direction?: "asc" | "desc" }
}

export type PredefinedCondition<T> = {
  items?: T[]
  reducing?: "OR" | "AND"
}
