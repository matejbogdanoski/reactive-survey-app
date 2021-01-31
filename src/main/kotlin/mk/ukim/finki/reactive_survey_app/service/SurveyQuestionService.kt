package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion


interface SurveyQuestionService {
    fun findAllBySurveyId(surveyId: Long): Flow<SurveyQuestion>
    suspend fun createSurveyQuestion(surveyId: Long): SurveyQuestion
    suspend fun deleteSurveyQuestion(surveyQuestionId: Long)
    suspend fun editSurveyQuestionInfo(surveyQuestionId: Long, questionType: String?,
                                       name: String?, isRequired: Boolean?): SurveyQuestion

    suspend fun duplicateSurveyQuestion(surveyId: Long, surveyQuestionId: Long): SurveyQuestion
    suspend fun updateSurveyQuestionPosition(surveyQuestionId: Long, newPosition: Int): SurveyQuestion
    suspend fun findById(questionId: Long): SurveyQuestion
}
