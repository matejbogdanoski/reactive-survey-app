package mk.ukim.finki.reactive_survey_app.security

import mk.ukim.finki.reactive_survey_app.security.jwt.JwtAuthenticationWebFilter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository

@EnableWebFluxSecurity
class SecurityConfig {
    companion object {
        val AUTH_WHITELIST = arrayOf(
                "/api/auth/**",
                "/api/users/signup"
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityContextRepository() = WebSessionServerSecurityContextRepository()

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity,
                                  authenticationWebFilter: JwtAuthenticationWebFilter,
                                  entryPoint: UnauthorizedAuthenticationEntryPoint): SecurityWebFilterChain? =
            http
                    .exceptionHandling()
                    .authenticationEntryPoint(entryPoint)
                    .and()
                    .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                    .authorizeExchange()
                    .pathMatchers(*AUTH_WHITELIST).permitAll()
                    .anyExchange().authenticated()
                    .and()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .csrf().disable()
                    .logout().disable().build()

}
