import { isEmpty, isNotEmpty } from "@/lib/utils"
import HttpStatusCodeError from "./HttpStatusCodeError"
import { PageRequest, PredefinedCondition } from "./types"

export const baseUrl = "http://localhost:8080/v1"

const apiClient = {
  async get<O>(path: string, query?: string) {
    return await exchange<any, O>({ line: { method: "GET", url: `${baseUrl}/${path}`, query } })
  },
  async post<I, O = unknown>(path: string, body: I) {
    return await exchange<I, O>({ line: { method: "POST", url: `${baseUrl}/${path}` }, entity: { body } })
  },
  async put<I, O = unknown>(path: string, body: I) {
    return await exchange<I, O>({ line: { method: "PUT", url: `${baseUrl}/${path}` }, entity: { body } })
  },
  async patch<I, O = unknown>(path: string, body: I) {
    return await exchange<I, O>({ line: { method: "PATCH", url: `${baseUrl}/${path}` }, entity: { body } })
  },
  async delete<O>(path: string, query?: string) {
    return await exchange<any, O>({ line: { method: "DELETE", url: `${baseUrl}/${path}`, query } })
  },
}

async function exchange<I = any, O = any>(request: Request<I>) {
  const { url, init } = buildRequest(request)
  const response = await fetch(url, init)
  return buildResponse<O>(response)
}

function buildRequest<I>(request: Request<I>) {
  const { line, entity } = request
  const url = `${line.url}${line.query ? `?${line.query}` : ""}`
  const headers =
    entity && !isEmpty(entity.headers)
      ? Object.entries(entity.headers!!).reduce((prev, [key, value]) => ({ ...prev, [key]: value.join(", ") }), {})
      : {}
  const init: RequestInit = {
    method: line.method,
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    body: entity && JSON.stringify(entity?.body),
  }
  return { url, init }
}

async function buildResponse<O>(response: globalThis.Response) {
  const headers = buildResponseHeaders(response.headers)
  const body = ![201, 204].includes(response.status) && (await response.json())
  const divided = Math.floor(response.status / 100)
  if (divided === 2) {
    return { status: response.status, entity: { headers, body } } as Response<O>
  } else {
    throw new HttpStatusCodeError(response.status, body)
  }
}

function buildResponseHeaders(headers: Headers) {
  const result = {} as Record<string, string[]>
  headers.forEach((value, key) => (result[key] = value.split(",").map((it) => it.trim())))
  return result
}

type Request<T = any> = {
  line: RequestLine
  entity?: HttpEntity<T>
}

type Response<T = any> = {
  status: number
  entity?: HttpEntity<T>
}

type RequestLine = {
  method: "GET" | "POST" | "PUT" | "PATCH" | "DELETE"
  url: string
  query?: string
}

type HttpEntity<T = any> = {
  headers?: Record<string, string[]>
  body?: T
}

export default apiClient

export function buildPageRequest(pageRequest: PageRequest) {
  const { page, size, sort } = pageRequest
  const params = new Array<string>()
  if (typeof page === "number") {
    params.push(`page=${page}`)
  }
  if (typeof size === "number") {
    params.push(`size=${size}`)
  }
  if (sort) {
    const { fields, direction = "asc" } = sort
    const prefix = fields.join(",")
    params.push(`sort=${prefix},${direction}`)
  }
  return params.join("&")
}

export function buildPredefinedConditions<T>(conditions?: PredefinedCondition<T>[]) {
  return conditions
    ?.map((it, index) => buildPredefinedCondition(`conditions[${index}]`, it))
    .filter(isNotEmpty)
    .join("&")
}

function buildPredefinedCondition<T>(prefix: string, condition: PredefinedCondition<T>) {
  const { items, reducing } = condition
  if (isEmpty(items)) {
    return undefined
  } else {
    const itemsQuery = items!.map((item) => `${encodeURIComponent(`${prefix}.items`)}=${item}`).join("&")
    if (reducing) {
      return `${itemsQuery}&reducing=${reducing}`
    } else {
      return itemsQuery
    }
  }
}
