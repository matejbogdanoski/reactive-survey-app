package mk.ukim.finki.reactive_survey_app.security.jwt.dto

data class JwtAuthenticationResponse(
        val token: String,
        val username: String
)
