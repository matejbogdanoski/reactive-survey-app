package mk.ukim.finki.reactive_survey_app.requests

data class UpdatePasswordRequest(
        val oldPassword: String,
        val newPassword: String,
        val confirmNewPassword: String,
        val token: String
)
