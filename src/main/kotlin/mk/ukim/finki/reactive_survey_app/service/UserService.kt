package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Mono

interface UserService {
    suspend fun createUser(username: String, password: String, email: String, firstName: String, lastName: String): User

    suspend fun findByUsername(username: String): User?

    fun findByUserNameMono(username: String): Mono<User>

    suspend fun editUserInfo(userId: Long, initiatedBy: Long, firstName: String?, lastName: String?, email: String?): User

    suspend fun updateUserPassword(username: String, oldPassword: String,
                                   newPassword: String, confirmNewPassword: String): Int

    suspend fun findById(userId: Long): User
}
