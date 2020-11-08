package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
data class QuestionAnswer(
        @Id
        val id: Long? = null,

        val surveyQuestionId: Long,

        val answer: String?,

        val answeredBy: Long?
) {
    companion object {
        const val TABLE_NAME = "question_answers"
    }
}
