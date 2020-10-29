package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse

object SurveyStaticMapper {

    fun mapSurveyToResponseStatic(it: Survey) =
            with(it) {
                SurveyResponse(id = id!!,
                               title = title,
                               description = description,
                               naturalKey = naturalKey,
                               canTakeAnonymously = canTakeAnonymously,
                               questions = questions.map(::mapSurveyQuestionToResponseStatic))
            }

    fun mapSurveyQuestionToResponseStatic(it: SurveyQuestion) =
            with(it) {
                SurveyQuestionResponse(id = id!!,
                                       questionType = QuestionType.values()[questionTypeId.toInt()],
                                       name = name,
                                       options =
                                       options?.map(::mapSurveyQuestionOptionToResponseStatic) ?: emptyList(),
                                       position = position,
                                       isRequired = isRequired)
            }

    fun mapSurveyQuestionOptionToResponseStatic(it: SurveyQuestionOption) =
            with(it) {
                SurveyQuestionOptionResponse(
                        id = id!!,
                        label = label,
                        position = position
                )
            }
}
