package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO

interface SurveyInstanceManagingService {
    suspend fun createInstanceWithAnswers(questionAnswerMap: Map<Long, String?>, surveyId: Long,
                                          takenBy: Long): SurveyInstance

    fun streamInstanceAnswers(surveyId: Long): Flow<AnswerDTO>
    fun findAllBySurveyId(surveyId: Long): Flow<SurveyInstance>
}
