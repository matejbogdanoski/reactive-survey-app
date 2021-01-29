package mk.ukim.finki.reactive_survey_app.mappers

import kotlinx.coroutines.reactor.mono
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
            //todo: change this later
            mono { surveyService.findById(surveyInvitation.surveyId) }.map {
                SurveyInvitationGridResponse(surveyNaturalKey = it.naturalKey,
                                             surveyTitle = it.title,
                                             surveyDescription = it.description)
            }

    suspend fun mapSurveyInvitationToResponse(surveyInvitation: SurveyInvitation): SurveyInvitationResponse =
            SurveyInvitationResponse(username = userService.findById(surveyInvitation.userId).username,
                    taken = surveyInvitation.taken)

}
