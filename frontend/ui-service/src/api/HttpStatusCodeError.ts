type ErrorBody = {
  code: string
  message: string
}

export default class HttpStatusCodeError extends Error {
  readonly httpStatus: number
  readonly body: ErrorBody

  constructor(httpStatus: number, body: ErrorBody) {
    super(body.message)
    this.httpStatus = httpStatus
    this.body = body
  }
}
