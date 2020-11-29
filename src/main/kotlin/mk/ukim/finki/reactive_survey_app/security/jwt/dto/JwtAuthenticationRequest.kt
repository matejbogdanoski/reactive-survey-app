package mk.ukim.finki.reactive_survey_app.security.jwt.dto

data class JwtAuthenticationRequest(
        val username: String,
        val password: String
)
