package mk.ukim.finki.reactive_survey_app.handlers

import kotlinx.coroutines.flow.map
import mk.ukim.finki.reactive_survey_app.mappers.toResponse
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

class SurveyQuestionHandler(
        private val service: SurveyQuestionService,
        private val managing: SurveyQuestionManagingService
) {
    suspend fun findAllBySurvey(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val surveyQuestions = service.findAllBySurveyId(surveyId).map { it.toResponse() }
        return ok().bodyAndAwait(surveyQuestions)
    }

    suspend fun createSurveyQuestion(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val surveyQuestion = service.createSurveyQuestion(surveyId).toResponse()
        return ok().bodyValueAndAwait(surveyQuestion)
    }

    suspend fun duplicateSurveyQuestion(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val surveyQuestionId = request.pathVariable("surveyQuestionId").toLong()
        val duplicateQuestion = managing.duplicateQuestion(surveyId, surveyQuestionId).toResponse()
        return ok().bodyValueAndAwait(duplicateQuestion)
    }

    suspend fun updateSurveyQuestionInfo(request: ServerRequest): ServerResponse {
        val surveyQuestionId = request.pathVariable("surveyQuestionId").toLong()
        val updateRequest = request.awaitBody<SurveyQuestionUpdateRequest>()
        val surveyQuestionResponse = with(updateRequest) {
            service.editSurveyQuestionInfo(
                    surveyQuestionId,
                    questionType = questionType,
                    name = name,
                    isRequired = isRequired,
            )
        }.toResponse()
        return ok().bodyValueAndAwait(surveyQuestionResponse)
    }

    suspend fun updateSurveyQuestionPosition(request: ServerRequest): ServerResponse {
        val surveyQuestionId = request.pathVariable("surveyQuestionId").toLong()
        val newPosition = request.pathVariable("newPosition").toInt()
        val surveyQuestionResponse = service.updateSurveyQuestionPosition(surveyQuestionId, newPosition).toResponse()
        return ok().bodyValueAndAwait(surveyQuestionResponse)
    }

    suspend fun deleteSurveyQuestion(request: ServerRequest): ServerResponse {
        val surveyQuestionId = request.pathVariable("surveyQuestionId").toLong()
        return ok().bodyValueAndAwait(service.deleteSurveyQuestion(surveyQuestionId))
    }

}
