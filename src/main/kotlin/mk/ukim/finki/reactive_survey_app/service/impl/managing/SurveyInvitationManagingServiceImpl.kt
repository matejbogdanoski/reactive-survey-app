package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyInvitationManagingServiceImpl(
        private val service: SurveyInvitationService,
        private val surveyService: SurveyService,
        private val userService: UserService
) : SurveyInvitationManagingService {

    override fun createSurveyInvitation(creator: Long, surveyId: Long, username: String): Mono<SurveyInvitation> {
        val surveyMono = surveyService.findById(surveyId)
        return userService.findByUsername(username).flatMap {
            service.createInvitation(surveyMono, creator, it.id!!)
        }.switchIfEmpty(Mono.error(IllegalArgumentException("Username does not exist!")))
    }

    override fun findInvitationsBySurvey(surveyId: Long, initiatedBy: Long): Flux<SurveyInvitation> {
        val survey = surveyService.findById(surveyId)
        return service.findInvitationsBySurvey(survey, initiatedBy)
    }

}
