package mk.ukim.finki.reactive_survey_app.responses

data class QuestionAnswerResponse(
        val questionId: Long,
        val questionName: String,
        val questionType: String,
        val answer: String?
)
