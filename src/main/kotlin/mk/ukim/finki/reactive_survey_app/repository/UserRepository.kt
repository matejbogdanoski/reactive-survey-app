package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByUsername(username: String): Mono<User>
}
