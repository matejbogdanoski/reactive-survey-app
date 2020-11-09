package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface QuestionAnswerService {
    fun createQuestionAnswer(surveyQuestionId: Long, answer: String?): Mono<QuestionAnswer>
    fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, Any?>): Flux<QuestionAnswer>
    fun getAnswerStream(questionId: Long): Flux<AnswerDTO?>
}
