package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInvitationManagingService {
    fun createSurveyInvitation(creator: String, surveyId: Long, username: String): Mono<SurveyInvitation>
    fun findInvitationsBySurvey(surveyId: Long, username: String): Flux<SurveyInvitation>
    fun findSurveyInvitationPage(username: String, page: Int, size: Int): Flux<SurveyInvitation>
    fun countAllSurveyInvitations(username: String): Mono<Int>
}
