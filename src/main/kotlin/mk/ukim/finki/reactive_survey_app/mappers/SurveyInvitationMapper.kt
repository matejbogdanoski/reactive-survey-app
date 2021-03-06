package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.responses.SurveyInvitationResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInvitationGridResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Component

@Component
class SurveyInvitationMapper(
        private val surveyService: SurveyService,
        private val userService: UserService
) {

    suspend fun mapSurveyInvitationToGridResponse(surveyInvitation: SurveyInvitation): SurveyInvitationGridResponse =
            surveyService.findById(surveyInvitation.surveyId).let {
                SurveyInvitationGridResponse(surveyNaturalKey = it.naturalKey,
                        surveyTitle = it.title,
                        surveyDescription = it.description)
            }

    suspend fun mapSurveyInvitationToResponse(surveyInvitation: SurveyInvitation): SurveyInvitationResponse =
            SurveyInvitationResponse(
                    username = userService.findById(surveyInvitation.userId).username,
                    taken = surveyInvitation.taken
            )

}
