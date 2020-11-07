package mk.ukim.finki.reactive_survey_app.responses

data class SurveyResponse(
        val id: Long,
        val title: String?,
        val description: String?,
        val naturalKey: String?,
        val canTakeAnonymously: Boolean
)
