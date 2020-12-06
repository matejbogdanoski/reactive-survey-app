package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.responses.SurveyInvitationResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInvitationGridResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SurveyInvitationMapper(
        private val surveyService: SurveyService,
        private val userService: UserService
) {

    fun mapSurveyInvitationToGridResponse(surveyInvitation: SurveyInvitation): Mono<SurveyInvitationGridResponse> =
            surveyService.findById(surveyInvitation.surveyId).map {
                SurveyInvitationGridResponse(surveyNaturalKey = it.naturalKey,
                                             surveyTitle = it.title,
                                             surveyDescription = it.description)
            }

    fun mapSurveyInvitationToResponse(surveyInvitation: SurveyInvitation): Mono<SurveyInvitationResponse> =
            userService.findById(surveyInvitation.userId).map {
                SurveyInvitationResponse(username = it.username,
                                         taken = surveyInvitation.taken)
            }

}
