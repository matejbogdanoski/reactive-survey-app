package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInvitationManagingService {
    fun createSurveyInvitation(creator: Long, surveyId: Long, username: String): Mono<SurveyInvitation>
    fun findInvitationsBySurvey(surveyId: Long, initiatedBy: Long): Flux<SurveyInvitation>
}
