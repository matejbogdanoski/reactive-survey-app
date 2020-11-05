package mk.ukim.finki.reactive_survey_app.requests

data class SurveyQuestionUpdateRequest(
        val questionType: String?,
        val name: String?,
        val isRequired: Boolean?
)
