export type FmTableRowData<T = any> = { id: string | number; data: T }
export type FmTableColumConfig<T = any> = { id: string | number; name: string; cellValue: (rowData: T) => React.ReactNode }
