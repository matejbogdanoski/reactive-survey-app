package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation

interface SurveyInvitationService {
    suspend fun createInvitation(survey: Survey, creator: Long, userId: Long): SurveyInvitation
    fun findInvitationsBySurvey(survey: Survey, initiatedBy: Long): Flow<SurveyInvitation>
    fun findInvitationsBySurveyNaturalKey(naturalKey: String): Flow<SurveyInvitation>
    fun findInvitationsBySurveyId(surveyId: Long): Flow<SurveyInvitation>
    fun findSurveyInvitationsPage(userId: Long, page: Int, size: Int): Flow<SurveyInvitation>
    suspend fun countAllByUserId(userId: Long): Int
    suspend fun markAsTaken(surveyId: Long, userId: Long): Int
}
