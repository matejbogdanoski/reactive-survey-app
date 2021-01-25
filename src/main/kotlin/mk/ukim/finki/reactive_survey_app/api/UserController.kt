package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.mappers.UserMapper
import mk.ukim.finki.reactive_survey_app.requests.UpdatePasswordRequest
import mk.ukim.finki.reactive_survey_app.requests.UserCreateRequest
import mk.ukim.finki.reactive_survey_app.requests.UserInfoUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.user.UserInfo
import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
        private val service: UserService,
        private val tokenUtils: JwtTokenUtils
) {

    @GetMapping("/user-info")
    suspend fun findUserInfo(@AuthenticationPrincipal principal: JwtAuthenticationToken): UserInfo =
            service.findById(principal.userId).let(UserMapper::mapUserToUserInfoResponse)

    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: UserCreateRequest): User =
            with(request) {
                service.createUser(username = username,
                        password = password,
                        email = email,
                        firstName = firstName,
                        lastName = lastName)
            }

    @PatchMapping("/user-info/{id}")
    suspend fun editUserInfo(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                             @RequestBody request: UserInfoUpdateRequest,
                             @PathVariable id: Long): UserInfo =
            service.editUserInfo(userId = id,
                    initiatedBy = principal.userId,
                    firstName = request.firstName,
                    lastName = request.lastName,
                    email = request.email).let(UserMapper::mapUserToUserInfoResponse)

    @PatchMapping("/update-password")
    suspend fun updatePassword(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                               @RequestBody request: UpdatePasswordRequest): JwtAuthenticationResponse = with(request) {
        service.updateUserPassword(username = principal.username!!,
                oldPassword = oldPassword,
                newPassword = newPassword,
                confirmNewPassword = confirmNewPassword)
        JwtAuthenticationResponse(token = tokenUtils.refreshToken(token), username = principal.username)
    }

}
