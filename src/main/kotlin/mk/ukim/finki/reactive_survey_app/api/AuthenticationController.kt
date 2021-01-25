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


@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
        private val userService: UserService,
        private val util: JwtTokenUtils,
        private val encoder: PasswordEncoder
) {

    @PostMapping
    suspend fun token(@RequestBody request: JwtAuthenticationRequest): JwtAuthenticationResponse {
        val user = userService.findByUsername(request.username)
        checkNotNull(user) { "User with username ${request.username} does not exist!" }
        return if (encoder.matches(request.password, user.passwordHash)) {
            JwtAuthenticationResponse(token = util.generateToken(user), username = user.username)
        } else {
            throw AccessDeniedException("Wrong password!")
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody token: String) = util.refreshToken(token)

}
