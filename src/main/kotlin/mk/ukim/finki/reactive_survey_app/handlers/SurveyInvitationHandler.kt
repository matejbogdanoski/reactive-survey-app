package mk.ukim.finki.reactive_survey_app.handlers

import kotlinx.coroutines.flow.map
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInvitationMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyInvitationRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.utils.activePrincipal
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

class SurveyInvitationHandler(
        private val managing: SurveyInvitationManagingService,
        private val service: SurveyInvitationService,
        private val mapper: SurveyInvitationMapper
) {

    suspend fun createSurveyInvitation(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val invitationRequest = request.awaitBody<SurveyInvitationRequest>()
        val invitation = managing.createSurveyInvitation(
                creator = principal.userId,
                surveyId = invitationRequest.surveyId,
                username = invitationRequest.username
        )
        return ok().bodyValueAndAwait(invitation)
    }

    suspend fun findInvitationsBySurvey(request: ServerRequest): ServerResponse {
        val surveyId = request.pathVariable("surveyId").toLong()
        val principal = request.activePrincipal()
        val invitations = managing.findInvitationsBySurvey(surveyId, principal.userId)
                .map(mapper::mapSurveyInvitationToResponse)
        return ok().bodyAndAwait(invitations)
    }

    suspend fun findSurveyInvitationsPage(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val size = checkNotNull(request.queryParamOrNull("size")) { "You must provide page size" }.toInt()
        val page = checkNotNull(request.queryParamOrNull("page")) { "You must provide page number" }.toInt()
        val invitationsPage = service.findSurveyInvitationsPage(principal.userId, page, size)
                .map(mapper::mapSurveyInvitationToGridResponse)
        return ok().bodyAndAwait(invitationsPage)
    }

    suspend fun countSurveyInvitations(request: ServerRequest): ServerResponse {
        val principal = request.activePrincipal()
        val count = service.countAllByUserId(principal.userId)
        return ok().bodyValueAndAwait(count)
    }

}
