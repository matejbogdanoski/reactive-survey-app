package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyQuestionOptionService {
    fun findAllBySurveyQuestionId(surveyQuestionId: Long): Flux<SurveyQuestionOption>
    fun createSurveyQuestionOption(surveyQuestionId: Long): Mono<SurveyQuestionOption>
    fun updateQuestionOptionLabel(surveyQuestionId: Long, optionId: Long, newLabel: String): Mono<SurveyQuestionOption>
    fun updateQuestionOptionPosition(surveyQuestionId: Long, optionId: Long,
                                     newPosition: Int): Mono<SurveyQuestionOption>

    fun deleteQuestionOption(surveyQuestionId: Long, optionId: Long): Mono<Void>
    fun duplicateAllBySurveyQuestionId(fromSurveyQuestionId: Long, toSurveyQuestionId: Long): Flow<SurveyQuestionOption>
}
