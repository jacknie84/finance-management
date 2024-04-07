export function isEmpty(target?: any) {
  if (!target) {
    return true
  }
  if (typeof target === "string") {
    return target.length > 0
  }
  if (typeof target === "object") {
    const length = Array.isArray(target) ? target.length : Object.keys(target).length
    return length <= 0
  }
  throw Error(`unsupported type: ${typeof target}`)
}
