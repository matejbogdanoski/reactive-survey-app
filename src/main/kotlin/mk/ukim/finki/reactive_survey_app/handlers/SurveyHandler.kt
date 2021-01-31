package mk.ukim.finki.reactive_survey_app.handlers

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.utils.activePrincipal
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok


class SurveyHandler(
        private val service: SurveyService
) {

    suspend fun findById(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val id = request.pathVariable("id").toLong()
        val survey = service.findById(id, principal.userId)
        return ok().bodyValueAndAwait(survey)
    }

    suspend fun findAllSurveysByUserPageable(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val size = checkNotNull(request.queryParamOrNull("size")) { "You must provide page size" }.toInt()
        val page = checkNotNull(request.queryParamOrNull("page")) { "You must provide page number" }.toInt()
        val surveys = service.findAllCreatedByPage(principal.userId, page, size)
                .map(SurveyStaticMapper::mapSurveyToResponseStatic).toList()
        return ok().bodyValueAndAwait(surveys)
    }

    suspend fun countAllSurveysByUser(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val createdBy = service.countAllCreatedBy(principal.userId)
        return ok().bodyValueAndAwait(createdBy)
    }

    suspend fun createSurvey(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val survey = service.createSurvey(principal.userId)
        return ok().bodyValueAndAwait(survey)
    }

    suspend fun updateSurvey(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val updateRequest = request.awaitBody<SurveyUpdateRequest>()
        return with(updateRequest) {
            service.updateSurvey(
                    id = surveyId,
                    title = title,
                    description = description,
                    canTakeAnonymously = canTakeAnonymously)
        }.let { ok().bodyValueAndAwait(it) }
    }

}
