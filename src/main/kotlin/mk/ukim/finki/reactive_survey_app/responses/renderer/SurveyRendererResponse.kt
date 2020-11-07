package mk.ukim.finki.reactive_survey_app.responses.renderer

data class SurveyRendererResponse(
        val id: Long,
        val title: String?,
        val description: String?,
        val naturalKey: String?,
        val canTakeAnonymously: Boolean,
        val questions: List<SurveyQuestionRendererResponse>
)
