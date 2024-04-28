import { debounce, isEmpty, uniq } from "lodash"

const distinct = uniq
const isNotEmpty = (value: any) => !isEmpty(value)

export { debounce, distinct, isEmpty, isNotEmpty }
