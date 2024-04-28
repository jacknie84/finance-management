package best.jacknie.finance.core.web.advice

import best.jacknie.finance.core.web.exception.HttpStatusCodeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorRestControllerAdvice {

  @ExceptionHandler(HttpStatusCodeException::class)
  fun handleHttpStatusCodeException(e: HttpStatusCodeException): ResponseEntity<*> {
    return ResponseEntity.status(e.status).body(Error(e.code, e.message))
  }

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*> {
    val firstError = e.bindingResult.allErrors.first()
    val code = firstError.code ?: "unknown"
    val message = if (firstError is FieldError) {
      "${firstError.defaultMessage}(field: ${firstError.field})"
    } else {
      firstError?.run { defaultMessage } ?: e.message
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error(code, message))
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
