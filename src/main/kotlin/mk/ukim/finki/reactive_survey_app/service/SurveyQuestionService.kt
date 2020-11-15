package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionUpdateRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface SurveyQuestionService {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyQuestion>
    fun createSurveyQuestion(surveyId: Long): Mono<SurveyQuestion>
    fun deleteSurveyQuestion(surveyQuestionId: Long): Mono<Void>
    fun editSurveyQuestionInfo(surveyQuestionId: Long, request: SurveyQuestionUpdateRequest): Mono<SurveyQuestion>
    fun duplicateSurveyQuestion(surveyId: Long, surveyQuestionId: Long): Flux<SurveyQuestion>
    fun updateSurveyQuestionPosition(surveyQuestionId: Long, newPosition: Int): Mono<SurveyQuestion>
    fun findById(questionId: Long): Mono<SurveyQuestion>
}
