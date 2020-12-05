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
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/users")
class UserController(
        private val service: UserService,
        private val tokenUtils: JwtTokenUtils
) {

    @GetMapping("/user-info")
    fun findUserInfo(@AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<UserInfo> =
            service.findByUsername(principal.username!!).map(UserMapper::mapUserToUserInfoResponse)

    @PostMapping("/signup")
    fun signUp(@RequestBody request: UserCreateRequest): Mono<User> =
            with(request) {
                service.createUser(username = username,
                                   password = password,
                                   email = email,
                                   firstName = firstName,
                                   lastName = lastName)
            }

    @PatchMapping("/user-info/{id}")
    fun editUserInfo(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                     @RequestBody request: UserInfoUpdateRequest,
                     @PathVariable id: Long): Mono<UserInfo> =
            service.editUserInfo(userId = id,
                                 initiatedBy = principal.username!!,
                                 firstName = request.firstName,
                                 lastName = request.lastName,
                                 email = request.email).map(UserMapper::mapUserToUserInfoResponse)

    @PatchMapping("/update-password")
    fun updatePassword(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                       @RequestBody request: UpdatePasswordRequest): Mono<JwtAuthenticationResponse> = with(request) {
        service.updateUserPassword(username = principal.username!!,
                                   oldPassword = oldPassword,
                                   newPassword = newPassword,
                                   confirmNewPassword = confirmNewPassword).then(
                Mono.just(JwtAuthenticationResponse(token = tokenUtils.refreshToken(token),
                                                    username = principal.username))
        )
    }

}
