package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.constants.PostgresNotificationNames.ANSWER_SAVED_NOTIFICATION
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.helper.PostgresNotificationListener
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInstanceMapper
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstanceResponse
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
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
        private val mapper: SurveyInstanceMapper
) : SurveyInstanceManagingService {

    override fun createInstanceWithAnswers(questionAnswerMap: Map<Long, Any?>, surveyId: Long): Mono<SurveyInstance> {
        val instance = surveyInstanceService.create(surveyId, 0, ZonedDateTime.now())
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

    override fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstanceResponse> =
            surveyInstanceService.findAllBySurveyId(surveyId).flatMap(mapper::mapSurveyInstanceToResponse)

}
