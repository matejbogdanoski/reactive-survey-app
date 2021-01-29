package mk.ukim.finki.reactive_survey_app.service.impl.managing

import kotlinx.coroutines.reactor.mono
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mk.ukim.finki.reactive_survey_app.constants.PostgresNotificationNames.ANSWER_SAVED_NOTIFICATION
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.helper.PostgresNotificationListener
import mk.ukim.finki.reactive_survey_app.service.*
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
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
        private val surveyInvitationService: SurveyInvitationService,
        private val surveyService: SurveyService
) : SurveyInstanceManagingService {

    override fun createInstanceWithAnswers(questionAnswerMap: Map<Long, String?>, surveyId: Long,
                                           takenBy: Long): Mono<SurveyInstance> {
        val invitationsFlux = surveyInvitationService.findInvitationsBySurveyId(surveyId)
        //todo: change this later
        val surveyMono = mono { surveyService.findById(surveyId) }
        return AccessValidator.validateCanCreateSurveyInstance(surveyMono, invitationsFlux, takenBy).flatMap {
            val survey = it.t1
            surveyInstanceService.create(survey.id!!, takenBy, ZonedDateTime.now()).doOnNext { instance ->
                questionAnswerService.bulkCreateQuestionAnswers(questionAnswerMap, instance.id!!).subscribe()
                surveyInvitationService.markAsTaken(survey.id, takenBy).subscribe()
            }
        }
    }

    override fun streamInstanceAnswers(surveyId: Long): Flux<AnswerDTO?> =
            postgresListener.listen(ANSWER_SAVED_NOTIFICATION)
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

}
