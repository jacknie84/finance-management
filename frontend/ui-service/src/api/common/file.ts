"use server"

import { baseUrl } from "../api-client"

const path = "files"

export async function putFile(formData: FormData) {
  const policy = formData.get("policy") as string
  const file = formData.get("file") as File
  const filename = Buffer.from(file.name, "ascii").toString("utf8")
  const body = new FormData()
  body.append("policy", policy)
  body.append("file", file, filename)
  const url = `${baseUrl}/${path}`
  const response = await fetch(url, { method: "put", body })
  return (await response.json()) as FileMetadata
}

export type FileMetadata = {
  id: string | number
  key: string
  name: string
  size: number
  contentType: string
}
