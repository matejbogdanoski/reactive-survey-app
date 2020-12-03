package mk.ukim.finki.reactive_survey_app.responses.user

data class UserInfo(
        val id: Long,
        val username: String,
        val firstName: String,
        val lastName: String,
        val email: String
)
