package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.responses.user.UserInfo

object UserMapper {

    fun mapUserToUserInfoResponse(user: User): UserInfo = with(user) {
        UserInfo(id = id!!,
                 username = username,
                 firstName = firstName,
                 lastName = lastName,
                 email = email)
    }

}
