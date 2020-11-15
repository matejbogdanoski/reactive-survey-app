package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInstanceManagingService {
    fun createInstanceWithAnswers(questionAnswerMap: Map<Long, Any?>, surveyId: Long): Mono<SurveyInstance>
    fun streamInstanceAnswers(surveyId: Long): Flux<AnswerDTO?>
}
