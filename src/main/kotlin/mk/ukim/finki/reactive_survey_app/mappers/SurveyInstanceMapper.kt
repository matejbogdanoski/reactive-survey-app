package mk.ukim.finki.reactive_survey_app.mappers

import kotlinx.coroutines.reactor.mono
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.QuestionAnswerResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstancePreview
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstanceResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInstanceGridResponse
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SurveyInstanceMapper(
        private val questionAnswerService: QuestionAnswerService,
        private val questionService: SurveyQuestionService,
        private val surveyService: SurveyService,
        private val rendererMapper: SurveyRendererMapper
) {

    fun mapSurveyInstanceToResponse(surveyInstance: SurveyInstance): Mono<SurveyInstanceResponse> = with(
            mapSurveyInstanceToResponseStatic(surveyInstance)) {
        questionAnswerService.findAllAnswersByInstanceId(id)
                .flatMap(::mapQuestionAnswerToResponse)
                .collectList()
                .map { copy(questionAnswers = it) }
    }


    fun mapQuestionAnswerToResponse(questionAnswer: QuestionAnswer): Mono<QuestionAnswerResponse> = with(
            mapQuestionAnswerToResponseStatic(questionAnswer)) {
        questionService.findById(questionAnswer.surveyQuestionId).map {
            copy(questionId = it.id!!,
                 questionName = it.name ?: "",
                 questionType = QuestionType.values()[it.questionTypeId.toInt()].name)
        }
    }

    fun mapSurveyInstanceToPreviewResponse(surveyInstance: SurveyInstance): Mono<SurveyInstancePreview> {
        val surveyInstanceMono = mapSurveyInstanceToResponse(surveyInstance)
        //todo: change this later
        val renderer = mono { surveyService.findById(surveyInstance.surveyId) }
                .flatMap(rendererMapper::mapSurveyToResponse)
        return surveyInstanceMono.zipWith(renderer).map {
            SurveyInstancePreview(survey = it.t2, surveyInstance = it.t1)
        }
    }

    fun mapSurveyInstanceToGridResponse(surveyInstance: SurveyInstance) = with(
            mapSurveyInstanceToGridResponseStatic(surveyInstance)) {
        //todo: change this later
        mono { surveyService.findById(surveyInstance.surveyId) }.map { copy(surveyTitle = it.title!!) }
    }

    private fun mapSurveyInstanceToGridResponseStatic(surveyInstance: SurveyInstance) = with(surveyInstance) {
        SurveyInstanceGridResponse(id = id!!,
                                   surveyTitle = "",
                                   dateTaken = dateTaken)
    }

    private fun mapSurveyInstanceToResponseStatic(surveyInstance: SurveyInstance) = with(surveyInstance) {
        SurveyInstanceResponse(id = id!!,
                               dateTaken = dateTaken.toString(),
                               questionAnswers = listOf())
    }

    private fun mapQuestionAnswerToResponseStatic(questionAnswer: QuestionAnswer) = with(questionAnswer) {
        QuestionAnswerResponse(questionId = 0,
                               questionName = "",
                               answer = answer,
                               questionType = "")
    }

}
