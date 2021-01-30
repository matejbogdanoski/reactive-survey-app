package mk.ukim.finki.reactive_survey_app.validators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import org.springframework.security.access.AccessDeniedException

object AccessValidator {

    suspend fun validateCanViewSurveyStructure(survey: Survey, surveyInvitations: Flow<SurveyInvitation>, initiatedBy: Long) {
        if (survey.createdBy == initiatedBy) return
        val userIds = surveyInvitations.filter { !it.taken }.map { it.userId }.toList()
        if (userIds.contains(initiatedBy)) return
        else throw AccessDeniedException("You do not have a permission to access this survey!")
    }

    suspend fun validateCanCreateSurveyInstance(survey: Survey, surveyInvitations: Flow<SurveyInvitation>,
                                                initiatedBy: Long) {
        if (survey.createdBy == initiatedBy) throw AccessDeniedException("You cannot submit answers to your own survey!")
        val userIds = surveyInvitations.filter { !it.taken }.map { it.userId }.toList()
        if (!userIds.contains(initiatedBy)) throw AccessDeniedException("You do not have a permission to submit answers to this survey!")
    }
}
