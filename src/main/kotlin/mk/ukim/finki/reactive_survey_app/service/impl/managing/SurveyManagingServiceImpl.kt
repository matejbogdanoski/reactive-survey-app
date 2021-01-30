package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.service.SurveyManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service

@Service
class SurveyManagingServiceImpl(
        private val surveyService: SurveyService,
        private val surveyQuestionService: SurveyQuestionService
) : SurveyManagingService {

    override suspend fun createSurveyWithQuestion(createdBy: Long): Survey {
        val survey = surveyService.createSurvey(createdBy)
        surveyQuestionService.createSurveyQuestion(survey.id!!)
        return survey
    }

}
