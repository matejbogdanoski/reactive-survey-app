package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
data class SurveyQuestion(
        @Id
        val id: Long? = null,

        val surveyId: Long,

        val questionTypeId: Long,

        val name: String?,

        val position: Int,

        val isRequired: Boolean
) {
    companion object {
        const val TABLE_NAME = "survey_questions"
    }
}
