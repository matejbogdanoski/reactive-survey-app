package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.service.SurveyRendererService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyRendererServiceImpl(
        private val surveyService: SurveyService,
        private val surveyInvitationService: SurveyInvitationService
) : SurveyRendererService {

    override fun findSurveyStructure(naturalKey: String, initiatedBy: Long): Mono<Survey> {
        val surveyMono = surveyService.findOneByNaturalKey(naturalKey)
        val surveyInvitationsFlux = surveyInvitationService.findInvitationsBySurveyNaturalKey(naturalKey)
        return AccessValidator.validateCanViewSurveyStructure(surveyMono, surveyInvitationsFlux, initiatedBy)
                .map { it.t1 }
    }
}
