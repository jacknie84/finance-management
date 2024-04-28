package best.jacknie.finance.core.jpa.utils

import org.h2.api.ErrorCode
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException

/**
 * JPA 예외가 키 중복 에러로 예외가 발생했는지 확인
 */
fun isDuplicateKeyError(e: DataIntegrityViolationException): Boolean {
  val cause = e.cause
  if (cause !is ConstraintViolationException) {
    return false;
  } else {
    return ErrorCode.DUPLICATE_KEY_1 == cause.sqlException.errorCode
  }
}
