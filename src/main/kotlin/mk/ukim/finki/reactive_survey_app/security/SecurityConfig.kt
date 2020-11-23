package mk.ukim.finki.reactive_survey_app.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? =
            http
                    .csrf().disable() //enable later
                    .authorizeExchange()
                    .pathMatchers("/api/users/signup").permitAll()
                    .anyExchange().authenticated()
                    .and()
                    .httpBasic().and()
                    .formLogin()
//                    .loginPage("")
                    .and().build()
}
