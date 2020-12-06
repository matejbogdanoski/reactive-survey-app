package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Mono

interface UserService {
    fun createUser(username: String, password: String, email: String, firstName: String, lastName: String): Mono<User>
    fun findByUsername(username: String): Mono<User>
    fun editUserInfo(userId: Long, initiatedBy: Long, firstName: String?, lastName: String?,
                     email: String?): Mono<User>

    fun updateUserPassword(username: String, oldPassword: String, newPassword: String,
                           confirmNewPassword: String): Mono<Int>

    fun findById(userId: Long): Mono<User>
}
