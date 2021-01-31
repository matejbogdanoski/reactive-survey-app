package mk.ukim.finki.reactive_survey_app.handlers

import kotlinx.coroutines.flow.map
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInstanceMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import mk.ukim.finki.reactive_survey_app.utils.activePrincipal
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

class SurveyInstanceHandler(
        private val service: SurveyInstanceService,
        private val managing: SurveyInstanceManagingService,
        private val mapper: SurveyInstanceMapper
) {
    suspend fun getAnswerStream(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val instanceAnswers = managing.streamInstanceAnswers(surveyId)
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).bodyAndAwait(instanceAnswers)
    }

    suspend fun findAllBySurvey(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val instances = managing.findAllBySurveyId(surveyId).map(mapper::mapSurveyInstanceToResponse)
        return ok().bodyAndAwait(instances)
    }

    suspend fun findAllTakenSurveysPage(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val size = checkNotNull(request.queryParamOrNull("size")) { "You must provide page size" }.toInt()
        val page = checkNotNull(request.queryParamOrNull("page")) { "You must provide page number" }.toInt()
        val instancesPage = service.findAllTakenByPage(principal.userId, size, page)
                .map(mapper::mapSurveyInstanceToGridResponse)
        return ok().bodyAndAwait(instancesPage)
    }

    suspend fun countAllTakenBy(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val countAllTakenBy = service.countAllTakenBy(principal.userId)
        return ok().bodyValueAndAwait(countAllTakenBy)
    }

    suspend fun findInstanceById(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val instanceId = request.pathVariable("instanceId").toLong()
        val instanceResponse = mapper.mapSurveyInstanceToPreviewResponse(
                surveyInstance = service.findById(instanceId, principal.userId)
        )
        return ok().bodyValueAndAwait(instanceResponse)
    }

    suspend fun createInstanceWithAnswers(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val surveyId = request.pathVariable("surveyId").toLong()
        val questionAnswerMap = request.awaitBody<Map<Long, String?>>()
        val instance = managing.createInstanceWithAnswers(questionAnswerMap, surveyId, principal.userId)
        return ok().bodyValueAndAwait(instance)
    }

}
