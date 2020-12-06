package mk.ukim.finki.reactive_survey_app.requests

data class SurveyInvitationRequest(
        val username: String,
        val surveyId: Long
)
