package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Mono

interface SurveyInvitationService {
    fun createInvitation(surveyMono: Mono<Survey>, userMono: Mono<User>): Mono<SurveyInvitation>
}
