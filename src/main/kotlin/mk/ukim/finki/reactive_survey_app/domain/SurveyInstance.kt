package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
class SurveyInstance(
        @Id
        val id: Long? = null,

        val surveyId: Long,

        val data: Map<String, Any?>?
) {
    companion object {
        const val TABLE_NAME = "survey_instances"
    }
}
