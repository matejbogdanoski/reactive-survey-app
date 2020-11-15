package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface QuestionAnswerService {
    fun createQuestionAnswer(surveyQuestionId: Long, answer: String?, surveyInstanceId: Long): Mono<QuestionAnswer>
    fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, Any?>, surveyInstanceId: Long): Flux<QuestionAnswer>
    fun findAllAnswersByQuestionId(questionId: Long): Flux<QuestionAnswer>
    fun findAllAnswersByInstanceId(instanceId: Long): Flux<QuestionAnswer>
}
