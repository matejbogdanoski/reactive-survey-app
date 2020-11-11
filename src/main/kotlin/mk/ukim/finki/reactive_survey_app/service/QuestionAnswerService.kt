package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface QuestionAnswerService {
    fun createQuestionAnswer(surveyQuestionId: Long, answer: String?, surveyId: Long): Mono<QuestionAnswer>
    fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, Any?>, surveyId: Long): Flux<QuestionAnswer>
    fun getAnswerStream(surveyId: Long): Flux<AnswerDTO?>
}
