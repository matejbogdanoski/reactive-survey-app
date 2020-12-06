package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.SURVEY}.${TABLE_NAME}")
data class SurveyInvitation(
        @Id
        val id: Long?,
        val surveyId: Long,
        val userId: Long,
        val taken: Boolean
) {
    companion object {
        const val TABLE_NAME = "survey_invitations"
    }
}
