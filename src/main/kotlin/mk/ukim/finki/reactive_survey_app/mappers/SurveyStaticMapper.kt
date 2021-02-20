package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse

object SurveyStaticMapper {

    fun mapSurveyToResponseStatic(survey: Survey) = with(survey) {
        SurveyResponse(id = id!!,
                       title = title,
                       description = description,
                       naturalKey = naturalKey)
    }

    fun mapSurveyQuestionToResponseStatic(surveyQuestion: SurveyQuestion) = with(surveyQuestion) {
        SurveyQuestionResponse(id = id!!,
                               questionType = QuestionType.fromOrdinal(questionTypeId.toInt()),
                               name = name,
                               position = position,
                               isRequired = isRequired)
    }

    fun mapSurveyQuestionOptionToResponseStatic(questionOption: SurveyQuestionOption) = with(questionOption) {
        SurveyQuestionOptionResponse(
                id = id!!,
                label = label,
                position = position
        )
    }
}
