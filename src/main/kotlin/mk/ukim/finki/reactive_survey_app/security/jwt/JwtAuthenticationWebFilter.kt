package mk.ukim.finki.reactive_survey_app.security.jwt

import mk.ukim.finki.reactive_survey_app.security.UnauthorizedAuthenticationEntryPoint
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationWebFilter(
        authenticationManager: ReactiveAuthenticationManager,
        converter: JwtAuthenticationConverter,
        entryPoint: UnauthorizedAuthenticationEntryPoint
) : AuthenticationWebFilter(authenticationManager) {
    init {
        setServerAuthenticationConverter(converter)
        setAuthenticationFailureHandler(ServerAuthenticationEntryPointFailureHandler(entryPoint))
        setRequiresAuthenticationMatcher(JWTHeadersExchangeMatcher())
    }

    private class JWTHeadersExchangeMatcher : ServerWebExchangeMatcher {
        override fun matches(exchange: ServerWebExchange): Mono<ServerWebExchangeMatcher.MatchResult> {
            val request = Mono.just(exchange).map { it.request }

            return request.map { it.headers }
                    .filter { it.containsKey("authorization") }
                    .flatMap { ServerWebExchangeMatcher.MatchResult.match() }
                    .switchIfEmpty(
                            request.map { it.queryParams }
                                    .filter { it.containsKey("token") }
                                    .flatMap { ServerWebExchangeMatcher.MatchResult.match() }
                                    .switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch())
                    )
        }
    }

}
