"use server"

import apiClient from "../api-client"
import { Page } from "../types"
import { User } from "./user"

const path = "cards"

export async function createCard(card: SaveCard) {
  await apiClient.post(path, card)
}

export async function getCardsPage() {
  const response = await apiClient.get<Page<Card>>(path)
  return response.entity?.body
}

export type SaveCard = {
  name: string
  number: string
  issuer: CardIssuer
  username: string
}

export type Card = {
  id: string | number
  name: string
  number: string
  issuer: CardIssuer
  user: User
}

export type CardIssuer = "KB"
