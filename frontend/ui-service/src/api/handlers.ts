import { FormState } from "@tanstack/react-form"
import { isRedirectError } from "next/dist/client/components/redirect"
import HttpStatusCodeError from "./HttpStatusCodeError"

export function handleErrorWithFormState<T>(e: any, formState: Partial<FormState<T>>) {
  if (isRedirectError(e)) {
    throw e
  }
  if (e instanceof HttpStatusCodeError) {
    const { httpStatus, body } = e
    const onServer = `[${httpStatus}: ${body.code}] ${body.message}`
    return { ...formState, errorMap: { onServer }, errors: [onServer] } as Partial<FormState<T>>
  } else {
    console.log(e)
    return { ...formState, errorMap: { onServer: e.message }, errors: [e.message] } as Partial<FormState<T>>
  }
}
