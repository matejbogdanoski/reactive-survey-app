package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionOptionToResponseStatic
import mk.ukim.finki.reactive_survey_app.responses.renderer.SurveyQuestionRendererResponse
import mk.ukim.finki.reactive_survey_app.responses.renderer.SurveyRendererResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SurveyRendererMapper(
        private val surveyQuestionService: SurveyQuestionService,
        private val surveyQuestionOptionService: SurveyQuestionOptionService
) {

    fun mapSurveyToResponse(survey: Survey): Mono<SurveyRendererResponse> = with(
            mapSurveyRendererToResponseStatic(survey)) {
        surveyQuestionService.findAllBySurveyId(id)
                .flatMap(::mapSurveyQuestionToResponse)
                .collectList()
                .map { copy(questions = it) }
    }

    fun mapSurveyQuestionToResponse(surveyQuestion: SurveyQuestion): Mono<SurveyQuestionRendererResponse> = with(
            mapSurveyQuestionRendererToResponse(surveyQuestion)) {
        surveyQuestionOptionService.findAllBySurveyQuestionId(id)
                .collectList()
                .map { copy(options = it.map(::mapSurveyQuestionOptionToResponseStatic)) }
    }

    private fun mapSurveyRendererToResponseStatic(survey: Survey) = with(survey) {
        SurveyRendererResponse(id = id!!,
                               title = title,
                               description = description,
                               naturalKey = naturalKey,
                               questions = emptyList())
    }

    private fun mapSurveyQuestionRendererToResponse(surveyQuestion: SurveyQuestion) = with(surveyQuestion) {
        SurveyQuestionRendererResponse(id = id!!,
                                       questionType = QuestionType.values()[questionTypeId.toInt()],
                                       options = emptyList(),
                                       name = name,
                                       position = position,
                                       isRequired = isRequired)
    }

}
