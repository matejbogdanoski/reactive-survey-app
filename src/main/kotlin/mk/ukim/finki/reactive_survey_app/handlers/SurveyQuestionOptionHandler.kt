package mk.ukim.finki.reactive_survey_app.handlers

import kotlinx.coroutines.flow.map
import mk.ukim.finki.reactive_survey_app.mappers.toResponse
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionOptionLabelUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

class SurveyQuestionOptionHandler(
        private val service: SurveyQuestionOptionService
) {
    suspend fun findAllByQuestion(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val questionOptions = service.findAllBySurveyQuestionId(questionId).map { it.toResponse() }
        return ok().bodyAndAwait(questionOptions)
    }

    suspend fun createQuestionOption(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val option = service.createSurveyQuestionOption(questionId).toResponse()
        return ok().bodyValueAndAwait(option)
    }

    suspend fun updateQuestionOptionLabel(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val questionOptionId = request.pathVariable("questionOptionId").toLong()
        val updateRequest = request.awaitBody<SurveyQuestionOptionLabelUpdateRequest>()
        val option = service.updateQuestionOptionLabel(
                surveyQuestionId = questionId,
                optionId = questionOptionId,
                newLabel = updateRequest.changedLabel).toResponse()
        return ok().bodyValueAndAwait(option)
    }

    suspend fun updateQuestionOptionPosition(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val questionOptionId = request.pathVariable("questionOptionId").toLong()
        val newPosition = request.pathVariable("newPosition").toInt()
        val option = service.updateQuestionOptionPosition(
                surveyQuestionId = questionId,
                optionId = questionOptionId,
                newPosition = newPosition).toResponse()
        return ok().bodyValueAndAwait(option)
    }

    suspend fun deleteQuestionOption(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val questionOptionId = request.pathVariable("questionOptionId").toLong()
        return ok().bodyValueAndAwait(service.deleteQuestionOption(questionId, questionOptionId))
    }

}
