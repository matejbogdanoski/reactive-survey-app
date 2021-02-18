package mk.ukim.finki.reactive_survey_app.responses

import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType

data class SurveyQuestionResponse(
        val id: Long,
        val questionType: QuestionType,
        val name: String?,
        val position: Int,
        val isRequired: Boolean
)
