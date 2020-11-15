package mk.ukim.finki.reactive_survey_app.responses

data class SurveyInstanceResponse(
        val id: Long,
        val dateTaken: String,
        val questionAnswers: List<QuestionAnswerResponse>
)
