package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion

interface SurveyQuestionManagingService {
    suspend fun duplicateQuestion(surveyId: Long, surveyQuestionId: Long): SurveyQuestion
}
