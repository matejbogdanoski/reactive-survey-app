package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.repository.QuestionAnswerRepository
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class QuestionAnswerServiceImpl(
        private val repository: QuestionAnswerRepository
) : QuestionAnswerService {

    override fun createQuestionAnswer(surveyQuestionId: Long, answer: String?,
                                      surveyInstanceId: Long): Mono<QuestionAnswer> = repository.save(
            QuestionAnswer(id = null,
                           surveyQuestionId = surveyQuestionId,
                           answer = answer,
                           surveyInstanceId = surveyInstanceId)
    )

    override fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, Any?>,
                                           surveyInstanceId: Long): Flux<QuestionAnswer> = questionAnswerMap.entries.map {
        QuestionAnswer(id = null,
                       surveyQuestionId = it.key,
                       answer = it.value.toString(),
                       surveyInstanceId = surveyInstanceId)
    }.toFlux().flatMap(repository::save)

    override fun findAllAnswersByQuestionId(
            questionId: Long): Flux<QuestionAnswer> = repository.findAllBySurveyQuestionId(questionId)
}
