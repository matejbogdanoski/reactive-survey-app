package mk.ukim.finki.reactive_survey_app.domain.dto

data class AnswerDTO(
        val id: Long,
        val survey_question_id: Long,
        val answer: String,
        val answered_by: Long?
)
