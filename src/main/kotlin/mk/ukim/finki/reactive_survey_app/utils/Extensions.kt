package mk.ukim.finki.reactive_survey_app.utils

import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitPrincipal

suspend fun ServerRequest.activePrincipal() = awaitPrincipal() as JwtAuthenticationToken
