package mk.ukim.finki.reactive_survey_app.security

import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReactiveUserDetailsService(
        private val userService: UserService
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> =
            username?.let(userService::findByUsername)?.map { user ->
                object : UserDetails {
                    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
                            AuthorityUtils.createAuthorityList("ROLE_USER")

                    override fun getPassword(): String = user.passwordHash

                    override fun getUsername(): String = user.username

                    override fun isAccountNonExpired(): Boolean = true

                    override fun isAccountNonLocked(): Boolean = true

                    override fun isCredentialsNonExpired(): Boolean = true

                    override fun isEnabled(): Boolean = true
                }
            } ?: Mono.empty()
}

