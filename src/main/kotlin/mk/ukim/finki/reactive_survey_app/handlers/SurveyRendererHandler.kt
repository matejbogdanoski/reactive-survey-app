package mk.ukim.finki.reactive_survey_app.handlers

import mk.ukim.finki.reactive_survey_app.mappers.SurveyRendererMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyRendererService
import mk.ukim.finki.reactive_survey_app.utils.activePrincipal
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class SurveyRendererHandler(
        private val service: SurveyRendererService,
        private val mapper: SurveyRendererMapper
) {

    suspend fun findSurveyRendererByNaturalKey(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val naturalKey = request.pathVariable("naturalKey")
        val surveyStructure = service.findSurveyStructure(naturalKey, principal.userId)
        return ok().bodyValueAndAwait(mapper.mapSurveyToResponse(surveyStructure))
    }
}
