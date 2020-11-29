package mk.ukim.finki.reactive_survey_app.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class UnauthorizedAuthenticationEntryPoint : ServerAuthenticationEntryPoint {
    override fun commence(exchange: ServerWebExchange, e: AuthenticationException?): Mono<Void> =
            Mono.fromRunnable { exchange.response.statusCode = HttpStatus.UNAUTHORIZED }
}
