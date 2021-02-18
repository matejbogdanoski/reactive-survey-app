package mk.ukim.finki.reactive_survey_app.responses.renderer

import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse

data class SurveyQuestionRendererResponse(
        val id: Long,
        val questionType: QuestionType,
        val options: List<SurveyQuestionOptionResponse>,
        val name: String?,
        val position: Int,
        val isRequired: Boolean
)
