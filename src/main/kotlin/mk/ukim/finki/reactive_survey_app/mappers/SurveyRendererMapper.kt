package mk.ukim.finki.reactive_survey_app.mappers

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionOptionToResponseStatic
import mk.ukim.finki.reactive_survey_app.responses.renderer.SurveyQuestionRendererResponse
import mk.ukim.finki.reactive_survey_app.responses.renderer.SurveyRendererResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Component

@Component
class SurveyRendererMapper(
        private val surveyQuestionService: SurveyQuestionService,
        private val surveyQuestionOptionService: SurveyQuestionOptionService
) {

    suspend fun mapSurveyToResponse(survey: Survey): SurveyRendererResponse =
            with(survey) {
                SurveyRendererResponse(
                        id = id!!,
                        title = title,
                        description = description,
                        naturalKey = naturalKey,
                        questions = surveyQuestionService.findAllBySurveyId(survey.id!!)
                                .map(::mapSurveyQuestionToResponse).toList()
                )
            }

    suspend fun mapSurveyQuestionToResponse(surveyQuestion: SurveyQuestion): SurveyQuestionRendererResponse =
            with(surveyQuestion) {
                SurveyQuestionRendererResponse(id = id!!,
                        questionType = QuestionType.fromOrdinal(questionTypeId.toInt()),
                        options = surveyQuestionOptionService.findAllBySurveyQuestionId(id)
                                .map(::mapSurveyQuestionOptionToResponseStatic).toList(),
                        name = name,
                        position = position,
                        isRequired = isRequired)
            }

}
