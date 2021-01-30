package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.UserRepository
import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationResponse
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val repository: UserRepository,
        private val encoder: PasswordEncoder,
        private val util: JwtTokenUtils
) : UserService {

    override suspend fun createUser(username: String, password: String, email: String,
                                    firstName: String, lastName: String): User =
            coroutineScope {
                val encodedPassword = withContext(Dispatchers.Default) { encoder.encode(password) }
                repository.save(
                        User(id = null,
                                username = username,
                                email = email,
                                firstName = firstName,
                                lastName = lastName,
                                passwordHash = encodedPassword)
                )
            }

    override suspend fun findByUsername(username: String): User? = repository.findByUsername(username)

    override suspend fun editUserInfo(userId: Long, initiatedBy: Long, firstName: String?,
                                      lastName: String?, email: String?): User =
            if (initiatedBy != userId) throw AccessDeniedException("You cannot edit others user info!")
            else {
                val user = checkNotNull(repository.findById(userId)) { "User with user id $userId does not exist!" }
                repository.updateUserInfo(userId = userId,
                        firstName = firstName ?: user.firstName,
                        lastName = lastName ?: user.lastName,
                        email = email ?: user.email)
                repository.findById(userId) ?: user
            }

    override suspend fun updateUserPassword(username: String, oldPassword: String,
                                            newPassword: String, confirmNewPassword: String): Int {
        if (newPassword != confirmNewPassword) throw IllegalArgumentException("Passwords do not match!")
        val user = checkNotNull(repository.findByUsername(username)) { "User with username $username does not exist" }
        if (!encoder.matches(oldPassword, user.passwordHash)) throw AccessDeniedException("Wrong password!")
        return coroutineScope {
            val encodedPassword = withContext(Dispatchers.Default) { encoder.encode(newPassword) }
            repository.updatePassword(user.id!!, encodedPassword)
        }
    }

    override suspend fun findById(userId: Long): User = repository.findById(userId)
            ?: throw NoSuchElementException("User with id $userId does not exist!")

    override suspend fun token(username: String, password: String): JwtAuthenticationResponse {
        val user = checkNotNull(findByUsername(username)) {
            "User with username $username does not exist!"
        }
        if (encoder.matches(password, user.passwordHash)) {
            return JwtAuthenticationResponse(token = util.generateToken(user), username = user.username)
        } else {
            throw AccessDeniedException("Wrong password!")
        }
    }

    override fun refreshToken(token: String): String = util.refreshToken(token)
}
