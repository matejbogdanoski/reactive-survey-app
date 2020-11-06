package mk.ukim.finki.reactive_survey_app.requests

data class SurveyQuestionUpdatePositionRequest(
        val previousIndex: Int,
        val currentIndex: Int
)
