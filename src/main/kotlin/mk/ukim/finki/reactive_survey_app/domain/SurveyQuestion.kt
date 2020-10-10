package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion.Companion.TABLE_NAME
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(TABLE_NAME)
data class SurveyQuestion(
        @Id
        val id: Long? = null,

        val survey: Survey,

        val questionType: QuestionType,

        val name: String?,

        val options: String?,

        val position: Int,

        val isRequired: Boolean
) {
    companion object {
        const val TABLE_NAME = "survey.survey_questions"
    }
}
