package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.SurveyInvitationRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyInvitationServiceImpl(
        private val repository: SurveyInvitationRepository
) : SurveyInvitationService {

    override fun createInvitation(surveyMono: Mono<Survey>, userMono: Mono<User>): Mono<SurveyInvitation> =
            AccessValidator.validateCanCreateSurveyInvitation(userMono, surveyMono).flatMap {
                val (user, survey) = it.t1 to it.t2
                repository.save(SurveyInvitation(id = null,
                                                 surveyId = survey.id!!,
                                                 userId = user.id!!,
                                                 taken = false))
            }

}
