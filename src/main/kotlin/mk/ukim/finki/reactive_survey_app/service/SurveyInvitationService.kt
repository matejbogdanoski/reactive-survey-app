package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInvitationService {
    fun createInvitation(surveyMono: Mono<Survey>, creator: Long, userId: Long): Mono<SurveyInvitation>
    fun findInvitationsBySurvey(surveyMono: Mono<Survey>, initiatedBy: Long): Flux<SurveyInvitation>
    fun findInvitationsBySurveyNaturalKey(naturalKey: String): Flux<SurveyInvitation>
    fun findInvitationsBySurveyId(surveyId: Long): Flux<SurveyInvitation>
    fun findSurveyInvitationsPage(userId: Long, page: Int, size: Int): Flux<SurveyInvitation>
    fun countAllByUserId(userId: Long): Mono<Int>
    fun markAsTaken(surveyId: Long, userId: Long): Mono<Int>
}
