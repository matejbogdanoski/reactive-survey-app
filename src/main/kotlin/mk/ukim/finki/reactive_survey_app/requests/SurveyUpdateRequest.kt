package mk.ukim.finki.reactive_survey_app.requests

data class SurveyUpdateRequest(
        val title: String?,
        val description: String?,
        val canTakeAnonymously: Boolean?
)
