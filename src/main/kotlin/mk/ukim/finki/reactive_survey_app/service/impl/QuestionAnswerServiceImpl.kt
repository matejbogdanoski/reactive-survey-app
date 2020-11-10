package mk.ukim.finki.reactive_survey_app.service.impl

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.api.Notification
import io.r2dbc.spi.ConnectionFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.repository.QuestionAnswerRepository
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class QuestionAnswerServiceImpl(
        private val repository: QuestionAnswerRepository,
        @Qualifier("connectionFactory")
        private val connectionFactory: ConnectionFactory
) : QuestionAnswerService {
    private final val connection: PostgresqlConnectionFactory =
            (connectionFactory as ConnectionPool).unwrap() as PostgresqlConnectionFactory
    val messages: Flux<Notification> = connection.create().flatMapMany {
        it.createStatement("LISTEN answer_saved_notification").execute().thenMany(it.notifications)
    }.share()

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
    }.toFlux().flatMap { repository.save(it) }

    override fun getAnswerStream(questionId: Long): Flux<AnswerDTO?> = messages.map {
        it.parameter?.let { json -> Json.decodeFromString<AnswerDTO>(json) }
    }.filter { it?.surveyQuestionId == questionId }

}
