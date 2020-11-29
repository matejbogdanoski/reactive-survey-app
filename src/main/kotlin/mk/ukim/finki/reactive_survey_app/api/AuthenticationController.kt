package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationRequest
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationResponse
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty


@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
        private val userService: UserService,
        private val util: JwtTokenUtils,
        private val encoder: PasswordEncoder
) {

    @PostMapping
    fun token(@RequestBody request: JwtAuthenticationRequest): Mono<JwtAuthenticationResponse> =
            userService.findByUsername(request.username)
                    .flatMap {
                        if (encoder.matches(request.password, it.passwordHash)) {
                            Mono.just(JwtAuthenticationResponse(token = util.generateToken(it), username = it.username))
                        } else {
                            Mono.error(AccessDeniedException("Wrong password!"))
                        }
                    }.switchIfEmpty {
                        Mono.error(AccessDeniedException("Username does not exist!"))
                    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody token: String) = util.refreshToken(token)

}
