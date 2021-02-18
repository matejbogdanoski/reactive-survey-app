package mk.ukim.finki.reactive_survey_app.mappers

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.QuestionAnswerResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstancePreview
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstanceResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInstanceGridResponse
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Component

@Component
class SurveyInstanceMapper(
    private val questionAnswerService: QuestionAnswerService,
    private val questionService: SurveyQuestionService,
    private val surveyService: SurveyService,
    private val rendererMapper: SurveyRendererMapper
) {

    suspend fun mapSurveyInstanceToResponse(surveyInstance: SurveyInstance): SurveyInstanceResponse =
        with(surveyInstance) {
            SurveyInstanceResponse(
                id = id!!,
                dateTaken = dateTaken.toString(),
                questionAnswers = questionAnswerService.findAllAnswersByInstanceId(id)
                    .map(::mapQuestionAnswerToResponse).toList()
            )
        }


    suspend fun mapQuestionAnswerToResponse(questionAnswer: QuestionAnswer): QuestionAnswerResponse {
        val question = questionService.findById(questionAnswer.surveyQuestionId)
        return QuestionAnswerResponse(
            questionId = question.id!!,
            questionName = question.name.orEmpty(),
            answer = questionAnswer.answer,
            questionType = QuestionType.values()[question.questionTypeId.toInt()].name
        )
    }

    suspend fun mapSurveyInstanceToPreviewResponse(surveyInstance: SurveyInstance): SurveyInstancePreview {
        val surveyInstanceResponse = mapSurveyInstanceToResponse(surveyInstance)
        val survey = surveyService.findById(surveyInstance.surveyId)
        return SurveyInstancePreview(
            survey = rendererMapper.mapSurveyToResponse(survey),
            surveyInstance = surveyInstanceResponse
        )
    }

    suspend fun mapSurveyInstanceToGridResponse(surveyInstance: SurveyInstance): SurveyInstanceGridResponse {
        val survey = surveyService.findById(surveyInstance.surveyId)
        return SurveyInstanceGridResponse(
            id = surveyInstance.id!!,
            surveyTitle = survey.title.orEmpty(),
            dateTaken = surveyInstance.dateTaken
        )
    }

}
