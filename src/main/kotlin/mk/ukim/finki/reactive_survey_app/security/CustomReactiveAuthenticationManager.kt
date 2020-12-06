package mk.ukim.finki.reactive_survey_app.security

import mk.ukim.finki.reactive_survey_app.security.dto.UserDetailsDto
import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtPreAuthenticationToken
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class CustomReactiveAuthenticationManager(
        private val userDetailsService: ReactiveUserDetailsService,
        private val utils: JwtTokenUtils
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> =
            if (authentication is JwtPreAuthenticationToken) {
                Mono.just<Authentication>(authentication)
                        .switchIfEmpty(Mono.defer { raiseBadCredentials() })
                        .cast(JwtPreAuthenticationToken::class.java)
                        .flatMap(::authenticateToken)
                        .publishOn(Schedulers.parallel())
                        .onErrorResume { raiseBadCredentials() }
                        .map {
                            val surveyUser = it as UserDetailsDto
                            JwtAuthenticationToken(token = null,
                                                   username = it.username,
                                                   authorities = it.authorities,
                                                   userId = surveyUser.userId)
                        }
            } else Mono.just(authentication)


    private fun <T> raiseBadCredentials(): Mono<T> = Mono.error(BadCredentialsException("Invalid Credentials"))

    private fun authenticateToken(jwtPreAuthenticationToken: JwtPreAuthenticationToken): Mono<UserDetails> {
        try {
            val authToken = jwtPreAuthenticationToken.authToken
            val username = jwtPreAuthenticationToken.username
            val bearerRequestHeader: String = jwtPreAuthenticationToken.bearerRequestHeader
            if (SecurityContextHolder.getContext().authentication == null) {
                if (utils.validateToken(authToken)) {
                    return userDetailsService.findByUsername(username)
                }
            }
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid token...")
        }
        return Mono.empty()
    }

}
