package mk.ukim.finki.reactive_survey_app.service.impl.managing

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.constants.PostgresNotificationNames.ANSWER_SAVED_NOTIFICATION
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.helper.PostgresNotificationListener
import mk.ukim.finki.reactive_survey_app.service.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

@Service
class SurveyInstanceManagingServiceImpl(
        private val surveyInstanceService: SurveyInstanceService,
        private val questionAnswerService: QuestionAnswerService,
        private val surveyQuestionService: SurveyQuestionService,
        private val postgresListener: PostgresNotificationListener,
        private val userService: UserService
) : SurveyInstanceManagingService {

    override fun createInstanceWithAnswers(questionAnswerMap: Map<Long, String?>, surveyId: Long,
                                           takenBy: String): Mono<SurveyInstance> {
        val instance = userService.findByUsername(takenBy).flatMap {
            surveyInstanceService.create(surveyId, it.id!!, ZonedDateTime.now())
        }
        return instance.doOnNext {
            questionAnswerService.bulkCreateQuestionAnswers(questionAnswerMap, it.id!!).subscribe()
        }
    }

    override fun streamInstanceAnswers(surveyId: Long): Flux<AnswerDTO?> = postgresListener.listen(
            ANSWER_SAVED_NOTIFICATION)
            .map { it.parameter?.let { json -> Json.decodeFromString<AnswerDTO>(json) } }
            .filterWhen { answer ->
                answer?.surveyInstanceId?.let { instanceId ->
                    surveyInstanceService.findById(instanceId).map { surveyInstance ->
                        surveyInstance.surveyId == surveyId
                    }
                }
            }.flatMap { answerDto ->
                answerDto?.let { answer ->
                    surveyQuestionService.findById(answer.surveyQuestionId).map {
                        answer.copy(questionName = it.name,
                                    questionType = QuestionType.values()[it.questionTypeId.toInt()].name)
                    }
                }
            }

    override fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstance> =
            surveyInstanceService.findAllBySurveyId(surveyId)

    override fun findAllTakenByPage(username: String, size: Int, page: Int): Flux<SurveyInstance> =
            userService.findByUsername(username)
                    .flatMapMany { surveyInstanceService.findAllTakenByPage(it.id!!, size, page) }

    override fun countAllByUsername(username: String): Mono<Int> = userService.findByUsername(username).flatMap {
        surveyInstanceService.countAllTakenBy(it.id!!)
    }

    override fun findById(instanceId: Long, username: String): Mono<SurveyInstance> =
            userService.findByUsername(username).let { surveyInstanceService.findById(instanceId, it) }
}
