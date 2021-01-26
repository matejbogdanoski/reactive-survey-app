package mk.ukim.finki.reactive_survey_app.security

import kotlinx.coroutines.reactor.mono
import mk.ukim.finki.reactive_survey_app.security.dto.UserDetailsDto
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReactiveUserDetailsService(
        private val userService: UserService
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        username?.let { userService.findByUsername(it) }
                ?.let {
                    UserDetailsDto(userName = it.username,
                            passwordHash = it.passwordHash,
                            userId = it.id!!)
                }
    }

}

