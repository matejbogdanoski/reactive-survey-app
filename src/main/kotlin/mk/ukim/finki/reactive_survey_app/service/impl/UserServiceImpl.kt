package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.UserRepository
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class UserServiceImpl(
        private val repository: UserRepository,
        private val encoder: PasswordEncoder
) : UserService {

    override fun createUser(username: String, password: String, email: String,
                            firstName: String, lastName: String): Mono<User> =
            Mono.just(User(id = null,
                           username = username,
                           email = email,
                           firstName = firstName,
                           lastName = lastName,
                           passwordHash = encoder.encode(password)))
                    .subscribeOn(Schedulers.parallel())
                    .flatMap(repository::save)

    override fun findByUsername(username: String): Mono<User> = repository.findByUsername(username)

}
