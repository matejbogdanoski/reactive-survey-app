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

    override fun createSurveyInvitation(creator: String, surveyId: Long, username: String): Mono<SurveyInvitation> {
        val creatorMono = userService.findByUsername(creator)
        val surveyMono = surveyService.findById(surveyId)
        return userService.findByUsername(username).flatMap {
            service.createInvitation(surveyMono, creatorMono, it.id!!)
        }.switchIfEmpty(Mono.error(IllegalArgumentException("Username does not exist!")))
    }

    override fun findInvitationsBySurvey(surveyId: Long, username: String): Flux<SurveyInvitation> {
        val user = userService.findByUsername(username)
        val survey = surveyService.findById(surveyId)
        return service.findInvitationsBySurvey(survey, user)
    }

    override fun findSurveyInvitationPage(username: String, page: Int, size: Int): Flux<SurveyInvitation> =
            userService.findByUsername(username).flatMapMany { service.findSurveyInvitationsPage(it.id!!, page, size) }

    override fun countAllSurveyInvitations(username: String): Mono<Int> =
            userService.findByUsername(username).flatMap { service.countAllByUserId(it.id!!) }
}
