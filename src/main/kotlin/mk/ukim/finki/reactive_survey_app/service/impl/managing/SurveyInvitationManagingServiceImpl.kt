package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyInvitationManagingServiceImpl(
        private val service: SurveyInvitationService,
        private val surveyService: SurveyService,
        private val userService: UserService
) : SurveyInvitationManagingService {

    override fun createSurveyInvitation(surveyId: Long, username: String): Mono<SurveyInvitation> {
        val userMono = userService.findByUsername(username)
        val surveyMono = surveyService.findById(surveyId)
        return service.createInvitation(surveyMono, userMono)
    }
}
