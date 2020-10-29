package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.Survey.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
data class Survey(
        @Id
        val id: Long? = null,

        val title: String?,

        val description: String?,

        val naturalKey: String,

        val canTakeAnonymously: Boolean,

        val questions: List<SurveyQuestion> = emptyList()

) {
    companion object {
        const val TABLE_NAME = "surveys"
    }
}
