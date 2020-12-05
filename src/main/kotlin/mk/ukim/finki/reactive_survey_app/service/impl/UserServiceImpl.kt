package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.UserRepository
import mk.ukim.finki.reactive_survey_app.service.UserService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
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

    override fun editUserInfo(userId: Long, initiatedBy: String, firstName: String?, lastName: String?,
                              email: String?): Mono<User> =
            AccessValidator.validateCanEditUserInfo(findByUsername(initiatedBy), userId).flatMap {
                repository.findById(userId).flatMap {
                    repository.updateUserInfo(userId = userId,
                                              firstName = firstName ?: it.firstName,
                                              lastName = lastName ?: it.lastName,
                                              email = email ?: it.email)
                }.then(repository.findById(userId))
            }

    override fun updateUserPassword(username: String, oldPassword: String,
                                    newPassword: String, confirmNewPassword: String): Mono<Int> {
        if (newPassword != confirmNewPassword) throw IllegalArgumentException("Passwords do not match!")
        return repository.findByUsername(username).handle { user, sink: SynchronousSink<User> ->
            if (!encoder.matches(oldPassword, user.passwordHash)) {
                sink.error(AccessDeniedException("Wrong Password !"))
            } else {
                sink.next(user)
            }
        }.map { it.id!! to encoder.encode(newPassword) }
                .subscribeOn(Schedulers.parallel())
                .flatMap { repository.updatePassword(it.first, it.second) }
    }
}
