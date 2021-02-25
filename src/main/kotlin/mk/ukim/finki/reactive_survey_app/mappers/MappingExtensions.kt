package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.domain.enums.QuestionType
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.responses.user.UserInfo

fun Survey.toResponse() = SurveyResponse(id = id!!,
                                         title = title,
                                         description = description,
                                         naturalKey = naturalKey)

fun SurveyQuestion.toResponse() = SurveyQuestionResponse(id = id!!,
                                                         questionType = QuestionType.fromOrdinal(
                                                                 questionTypeId.toInt()),
                                                         name = name,
                                                         position = position,
                                                         isRequired = isRequired)

fun SurveyQuestionOption.toResponse() = SurveyQuestionOptionResponse(
        id = id!!,
        label = label,
        position = position)

fun User.toInfoResponse() = UserInfo(id = id!!,
                                     username = username,
                                     firstName = firstName,
                                     lastName = lastName,
                                     email = email)
