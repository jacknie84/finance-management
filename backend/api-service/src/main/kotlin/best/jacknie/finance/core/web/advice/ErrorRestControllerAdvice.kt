package best.jacknie.finance.core.web.advice

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorRestControllerAdvice {

  @ExceptionHandler(HttpStatusCodeException::class)
  fun handleHttpStatusCodeException(e: HttpStatusCodeException): ResponseEntity<*> {
    return ResponseEntity.status(e.status).body(Error(e.code, e.message))
  }

  data class Error(

    /**
     * 에러 코드
     */
    val code: String,

    /**
     * 에러 메시지
     */
    val message: String?,
  )
}
