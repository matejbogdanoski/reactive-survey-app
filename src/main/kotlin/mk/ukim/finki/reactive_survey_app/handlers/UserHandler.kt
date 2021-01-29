package mk.ukim.finki.reactive_survey_app.handlers

import mk.ukim.finki.reactive_survey_app.mappers.UserMapper
import mk.ukim.finki.reactive_survey_app.requests.UpdatePasswordRequest
import mk.ukim.finki.reactive_survey_app.requests.UserCreateRequest
import mk.ukim.finki.reactive_survey_app.requests.UserInfoUpdateRequest
import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationResponse
import mk.ukim.finki.reactive_survey_app.service.UserService
import mk.ukim.finki.reactive_survey_app.utils.activePrincipal
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait


class UserHandler(
        private val service: UserService,
        private val tokenUtils: JwtTokenUtils
) {

    suspend fun findUserInfo(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val userInfo = service.findById(principal.userId).let(UserMapper::mapUserToUserInfoResponse)
        return ok().bodyValueAndAwait(userInfo)
    }

    suspend fun signup(request: ServerRequest): ServerResponse {
        val userCreateRequest = request.awaitBody<UserCreateRequest>()
        return with(userCreateRequest) {
            service.createUser(username = username,
                    password = password,
                    email = email,
                    firstName = firstName,
                    lastName = lastName)
        }.let { ok().bodyValueAndAwait(it) }
    }

    suspend fun editUserInfo(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val userInfoUpdateRequest = request.awaitBody<UserInfoUpdateRequest>()
        val id = request.pathVariable("id").toLong()
        val userInfo = service.editUserInfo(userId = id,
                initiatedBy = principal.userId,
                firstName = userInfoUpdateRequest.firstName,
                lastName = userInfoUpdateRequest.lastName,
                email = userInfoUpdateRequest.email).let(UserMapper::mapUserToUserInfoResponse)
        return ok().bodyValueAndAwait(userInfo)
    }

    suspend fun updatePassword(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val updatePasswordRequest = request.awaitBody<UpdatePasswordRequest>()
        return with(updatePasswordRequest) {
            service.updateUserPassword(username = principal.username!!,
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                    confirmNewPassword = confirmNewPassword)
            JwtAuthenticationResponse(token = tokenUtils.refreshToken(token), username = principal.username)
        }.let { ok().bodyValueAndAwait(it) }
    }

}
