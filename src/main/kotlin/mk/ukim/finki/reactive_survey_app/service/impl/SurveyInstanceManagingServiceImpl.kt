package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.constants.PostgresNotificationNames.ANSWER_SAVED_NOTIFICATION
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.helper.PostgresNotificationListener
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

@Service
class SurveyInstanceManagingServiceImpl(
        private val surveyInstanceService: SurveyInstanceService,
        private val questionAnswerService: QuestionAnswerService,
        private val postgresListener: PostgresNotificationListener
) : SurveyInstanceManagingService {

    override fun createInstanceWithAnswers(questionAnswerMap: Map<Long, Any?>, surveyId: Long): Mono<SurveyInstance> {
        val instance = surveyInstanceService.create(surveyId, 0, ZonedDateTime.now())
//        val flatMapMany = instance.flatMapMany {
//            questionAnswerService.bulkCreateQuestionAnswers(questionAnswerMap, it.id!!)
//        }
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
            }
}
