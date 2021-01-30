package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyQuestionOptionService {
    fun findAllBySurveyQuestionId(surveyQuestionId: Long): Flow<SurveyQuestionOption>
    suspend fun createSurveyQuestionOption(surveyQuestionId: Long): SurveyQuestionOption
    suspend fun updateQuestionOptionLabel(surveyQuestionId: Long, optionId: Long, newLabel: String): SurveyQuestionOption
    suspend fun updateQuestionOptionPosition(surveyQuestionId: Long, optionId: Long,
                                             newPosition: Int): SurveyQuestionOption

    suspend fun deleteQuestionOption(surveyQuestionId: Long, optionId: Long)
    fun duplicateAllBySurveyQuestionId(fromSurveyQuestionId: Long, toSurveyQuestionId: Long): Flow<SurveyQuestionOption>
}
