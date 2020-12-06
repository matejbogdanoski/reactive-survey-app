package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInvitationService {
    fun createInvitation(surveyMono: Mono<Survey>, creatorMono: Mono<User>, userId: Long): Mono<SurveyInvitation>
    fun findInvitationsBySurvey(surveyMono: Mono<Survey>, userMono: Mono<User>): Flux<SurveyInvitation>
    fun findSurveyInvitationsPage(userId: Long, page: Int, size: Int): Flux<SurveyInvitation>
}
