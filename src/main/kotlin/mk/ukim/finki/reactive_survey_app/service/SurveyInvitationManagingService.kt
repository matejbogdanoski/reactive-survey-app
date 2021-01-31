package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation

interface SurveyInvitationManagingService {
    suspend fun createSurveyInvitation(creator: Long, surveyId: Long, username: String): SurveyInvitation
    suspend fun findInvitationsBySurvey(surveyId: Long, initiatedBy: Long): Flow<SurveyInvitation>
}
