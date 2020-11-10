package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.constants.PostgresNotificationNames.ANSWER_SAVED_NOTIFICATION
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.helper.PostgresNotificationListener
import mk.ukim.finki.reactive_survey_app.repository.QuestionAnswerRepository
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class QuestionAnswerServiceImpl(
        private val repository: QuestionAnswerRepository,
        private val postgresListener: PostgresNotificationListener
) : QuestionAnswerService {

    override fun createQuestionAnswer(surveyQuestionId: Long, answer: String?): Mono<QuestionAnswer> = repository.save(
            QuestionAnswer(id = null,
                           surveyQuestionId = surveyQuestionId,
                           answer = answer,
                           answeredBy = null)
    )

    override fun bulkCreateQuestionAnswers(
            questionAnswerMap: Map<Long, Any?>): Flux<QuestionAnswer> = questionAnswerMap.entries.map {
        QuestionAnswer(id = null,
                       surveyQuestionId = it.key,
                       answer = it.value.toString(),
                       answeredBy = null)
    }.toFlux().flatMap(repository::save)

    override fun getAnswerStream(questionId: Long): Flux<AnswerDTO?> =
            postgresListener.listen(ANSWER_SAVED_NOTIFICATION)
                    .map { it.parameter?.let { json -> Json.decodeFromString<AnswerDTO>(json) } }
                    .filter { it?.surveyQuestionId == questionId }

}
