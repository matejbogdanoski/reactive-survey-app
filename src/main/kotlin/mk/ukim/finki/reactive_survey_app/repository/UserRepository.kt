package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByUsername(username: String): Mono<User>

    @Modifying
    @Query("update access.users set first_name = :firstName, last_name = :lastName, email = :email where id = :userId")
    fun updateUserInfo(userId: Long, firstName: String, lastName: String, email: String): Mono<Int>
}
