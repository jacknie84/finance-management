package best.jacknie.finance.core.web.exception

import org.springframework.http.HttpStatus

sealed class HttpStatusCodeException: RuntimeException {

  val status: HttpStatus
  val code: String

  constructor(status: HttpStatus): this(status, status.toString(), status.reasonPhrase)

  constructor(status: HttpStatus, message: String?): this(status, message, status.reasonPhrase)

  constructor(status: HttpStatus, message: String?, code: String): super(message) {
    this.status = status
    this.code = code
  }

  /**
   * 400 Bad Request
   */
  class BadRequest(message: String?): HttpStatusCodeException(HttpStatus.BAD_REQUEST, message)

  /**
   * 404 Not Found
   */
  class NotFound(message: String?): HttpStatusCodeException(HttpStatus.NOT_FOUND, message)

  /**
   * 409 Conflict
   */
  class Conflict: HttpStatusCodeException {

    constructor(code: String, message: String?): super(HttpStatus.CONFLICT, message, code)

    constructor(message: String?): super(HttpStatus.CONFLICT, message)
  }

  /**
   * 500 Internal Server Error
   */
  class InternalServerError(message: String?): HttpStatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, message)
}
