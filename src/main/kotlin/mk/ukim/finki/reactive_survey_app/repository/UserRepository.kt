package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findByUsername(username: String): User?

    @Modifying
    @Query("update access.users set first_name = :firstName, last_name = :lastName, email = :email where id = :userId")
    suspend fun updateUserInfo(userId: Long, firstName: String, lastName: String, email: String): Int

    @Modifying
    @Query("update access.users set password_hash = :password where id = :userId")
    suspend fun updatePassword(userId: Long, password: String): Int
}
