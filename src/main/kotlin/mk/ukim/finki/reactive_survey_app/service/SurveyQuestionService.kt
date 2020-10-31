package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface SurveyQuestionService {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyQuestion>
    fun createSurveyQuestion(surveyId: Long): Mono<SurveyQuestion>
}
