package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import reactor.core.publisher.Mono

interface SurveyInvitationManagingService {
    fun createSurveyInvitation(surveyId: Long, username: String): Mono<SurveyInvitation>
}
