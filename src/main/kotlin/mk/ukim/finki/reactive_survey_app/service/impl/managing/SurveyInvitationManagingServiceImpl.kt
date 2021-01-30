package mk.ukim.finki.reactive_survey_app.service.impl.managing

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Service

@Service
class SurveyInvitationManagingServiceImpl(
    private val service: SurveyInvitationService,
    private val surveyService: SurveyService,
    private val userService: UserService
) : SurveyInvitationManagingService {

    override suspend fun createSurveyInvitation(creator: Long, surveyId: Long, username: String): SurveyInvitation {
        val survey = surveyService.findById(surveyId)
        val user = userService.findByUsername(username)
        checkNotNull(user) { "Username does not exist!" }
        return service.createInvitation(survey, creator, user.id!!)
    }

    override suspend fun findInvitationsBySurvey(surveyId: Long, initiatedBy: Long): Flow<SurveyInvitation> {
        val survey = surveyService.findById(surveyId)
        return service.findInvitationsBySurvey(survey, initiatedBy)
    }

}
