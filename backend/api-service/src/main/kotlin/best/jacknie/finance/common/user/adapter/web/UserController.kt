package best.jacknie.finance.common.user.adapter.web

import best.jacknie.finance.common.user.application.port.PatchUser
import best.jacknie.finance.common.user.application.port.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
  private val userService: UserService
) {

  @GetMapping
  fun getUsersPage(pageable: Pageable): ResponseEntity<*> {
    val page = userService.getUsersPage(pageable)
    return ResponseEntity.ok(page)
  }

  @PatchMapping("/{id}")
  fun patchUser(@PathVariable("id") id: Long, @RequestBody @Valid dto: PatchUser): ResponseEntity<*> {
    userService.patchUser(id, dto)
    return ResponseEntity.noContent().build<Any>()
  }
}
