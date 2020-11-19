package mk.ukim.finki.reactive_survey_app.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerDTO(
        val id: Long,
        @SerialName("survey_question_id")
        val surveyQuestionId: Long,
        @SerialName("survey_instance_id")
        val surveyInstanceId: Long,
        val answer: String,
        val questionName: String? = null,
        val questionType: String? = null
)
