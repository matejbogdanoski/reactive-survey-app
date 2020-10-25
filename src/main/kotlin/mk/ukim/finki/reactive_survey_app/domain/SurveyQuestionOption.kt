package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
data class SurveyQuestionOption(
        @Id
        val id: Long?,

        val surveyQuestionId: Long,

        val label: String,

        val position: Int
) {
    companion object {
        const val TABLE_NAME = "survey_question_options"
    }
}
