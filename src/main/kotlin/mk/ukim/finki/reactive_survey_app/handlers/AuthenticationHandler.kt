package mk.ukim.finki.reactive_survey_app.handlers

import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationRequest
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class AuthenticationHandler(
        private val userService: UserService
) {

    suspend fun token(request: ServerRequest): ServerResponse {
        val authRequest = request.awaitBody<JwtAuthenticationRequest>()
        val token = userService.token(username = authRequest.username, password = authRequest.password)
        return ok().bodyValueAndAwait(token)
    }

    suspend fun refreshToken(request: ServerRequest): ServerResponse {
        val token = request.awaitBody<String>()
        val refreshToken = userService.refreshToken(token)
        return ok().bodyValueAndAwait(refreshToken)
    }

}
