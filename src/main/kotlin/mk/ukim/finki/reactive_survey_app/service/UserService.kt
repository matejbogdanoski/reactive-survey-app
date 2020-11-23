package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Mono

interface UserService {
    fun createUser(username: String, password: String, email: String, firstName: String, lastName: String): Mono<User>
    fun findByUsername(username: String): Mono<User>
}
