package mk.ukim.finki.reactive_survey_app.requests

data class UserInfoUpdateRequest(
        val firstName: String?,
        val lastName: String?,
        val email: String?
)
