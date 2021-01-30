package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Service

@Service
class SurveyQuestionManagingServiceImpl(
        private val questionService: SurveyQuestionService,
        private val optionService: SurveyQuestionOptionService
) : SurveyQuestionManagingService {

    override suspend fun duplicateQuestion(surveyId: Long, surveyQuestionId: Long): SurveyQuestion {
        val duplicateSurveyQuestion = questionService.duplicateSurveyQuestion(surveyId, surveyQuestionId)
        optionService.duplicateAllBySurveyQuestionId(surveyQuestionId, duplicateSurveyQuestion.id!!)
        return duplicateSurveyQuestion
    }

}
