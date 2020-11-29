package mk.ukim.finki.reactive_survey_app.security.jwt

import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtPreAuthenticationToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class JwtAuthenticationConverter(
        private val jwtTokenUtil: JwtTokenUtils,
        @Value("\${jwt.header}")
        private val tokenHeader: String,

        @Value("\${jwt.param}")
        private val tokenParam: String,

        @Value("\${jwt.prefix}")
        private val bearerPrefix: String
) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val request = exchange.request
        return try {
            val authentication: Authentication? = null
            var authToken: String? = null
            var username: String? = null
            val bearerRequestHeader = exchange.request.headers.getFirst(tokenHeader)

            if (bearerRequestHeader != null && bearerRequestHeader.startsWith("$bearerPrefix ")) {
                authToken = bearerRequestHeader.substring(7)
            }
            if (authToken == null && !request.queryParams.isEmpty()) {
                val authTokenParam = request.queryParams.getFirst(tokenParam)
                if (authTokenParam != null) authToken = authTokenParam
            }
            if (authToken != null) {
                try {
                    username = jwtTokenUtil.getUsernameFromToken(authToken)
                } catch (e: IllegalArgumentException) {
                    System.err.println("an error occured during getting username from token")
                    System.err.println(e.message)
                } catch (e: Exception) {
                    System.err.println("the token is expired and not valid anymore")
                    System.err.println(e.message)
                }
            }
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                Mono.just(JwtPreAuthenticationToken(authToken!!, bearerRequestHeader!!, username))
            } else Mono.just(authentication!!)

        } catch (e: Exception) {
            throw BadCredentialsException("Invalid token...")
        }
    }

}
