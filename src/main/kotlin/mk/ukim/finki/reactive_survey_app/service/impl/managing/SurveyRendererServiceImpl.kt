package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.service.SurveyRendererService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.stereotype.Service

@Service
class SurveyRendererServiceImpl(
        private val surveyService: SurveyService,
        private val surveyInvitationService: SurveyInvitationService
) : SurveyRendererService {

    override suspend fun findSurveyStructure(naturalKey: String, initiatedBy: Long): Survey {
        val survey = surveyService.findOneByNaturalKey(naturalKey)
        val surveyInvitations = surveyInvitationService.findInvitationsBySurveyNaturalKey(naturalKey)
        AccessValidator.validateCanViewSurveyStructure(survey, surveyInvitations, initiatedBy)
        return survey
    }
}
