package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.requests.UserCreateRequest
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/users")
class UserController(
        private val service: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: UserCreateRequest): Mono<User> =
            with(request) {
                service.createUser(username = username,
                                   password = password,
                                   email = email,
                                   firstName = firstName,
                                   lastName = lastName)
            }

}
