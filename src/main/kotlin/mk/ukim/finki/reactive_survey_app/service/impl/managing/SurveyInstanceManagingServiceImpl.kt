package mk.ukim.finki.reactive_survey_app.service.impl.managing

import kotlinx.coroutines.flow.*
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

    override suspend fun createInstanceWithAnswers(questionAnswerMap: Map<Long, String?>, surveyId: Long,
                                                   takenBy: Long): SurveyInstance {
        val invitations = surveyInvitationService.findInvitationsBySurveyId(surveyId)
        val survey = surveyService.findById(surveyId)
        AccessValidator.validateCanCreateSurveyInstance(survey, invitations, takenBy)
        val surveyInstance = surveyInstanceService.create(survey.id!!, takenBy, ZonedDateTime.now())
        questionAnswerService.bulkCreateQuestionAnswers(questionAnswerMap, surveyInstance.id!!).collect()
        surveyInvitationService.markAsTaken(survey.id, takenBy)
        return surveyInstance
    }

    override fun streamInstanceAnswers(surveyId: Long): Flow<AnswerDTO> =
            postgresListener.listen(ANSWER_SAVED_NOTIFICATION)
                    .map { it.parameter?.let { json -> Json.decodeFromString<AnswerDTO>(json) } }
                    .filterNotNull()
                    .filter { answer ->
                        val surveyInstance = surveyInstanceService.findById(answer.surveyInstanceId)
                        surveyInstance.surveyId == surveyId
                    }.map { answerDto ->
                        val question = surveyQuestionService.findById(answerDto.surveyQuestionId)
                        answerDto.copy(questionName = question.name,
                                questionType = QuestionType.values()[question.questionTypeId.toInt()].name)
                    }

    override fun findAllBySurveyId(surveyId: Long): Flow<SurveyInstance> =
            surveyInstanceService.findAllBySurveyId(surveyId)

}
