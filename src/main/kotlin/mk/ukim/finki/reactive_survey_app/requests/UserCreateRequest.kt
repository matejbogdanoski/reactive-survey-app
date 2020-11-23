package mk.ukim.finki.reactive_survey_app.requests

data class UserCreateRequest(
        val username: String,
        val password: String,
        val email: String,
        val firstName: String,
        val lastName: String
)
