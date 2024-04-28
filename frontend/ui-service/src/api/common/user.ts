"use server"

import apiClient from "@/api/api-client"
import { Page } from "@/api/types"

const path = "users"

export async function getUsers() {
  const response = await apiClient.get<Page<User>>(path)
  return response.entity?.body?.content
}

export type User = { id: string | number; name: string; displayName?: string }
