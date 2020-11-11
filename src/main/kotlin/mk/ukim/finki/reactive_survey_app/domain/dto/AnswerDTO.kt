package mk.ukim.finki.reactive_survey_app.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerDTO(
        val id: Long,
        @SerialName("survey_question_id")
        val surveyQuestionId: Long,
        val answer: String,
        @SerialName("answered_by")
        val answeredBy: Long?,
        @SerialName("survey_id")
        val surveyId: Long
)
