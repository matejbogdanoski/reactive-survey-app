package mk.ukim.finki.reactive_survey_app.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInvitationMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyInvitationRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyInvitationResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInvitationGridResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/survey-invitations")
class SurveyInvitationController(
        private val managing: SurveyInvitationManagingService,
        private val service: SurveyInvitationService,
        private val mapper: SurveyInvitationMapper
) {

    @PostMapping
    suspend fun createSurveyInvitation(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                       @RequestBody request: SurveyInvitationRequest): Mono<SurveyInvitation> =
            with(request) { managing.createSurveyInvitation(principal.userId, surveyId, username) }

    @GetMapping("/{surveyId}")
    suspend fun findInvitationsBySurvey(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                @PathVariable surveyId: Long): Flow<SurveyInvitationResponse> =
            managing.findInvitationsBySurvey(surveyId, principal.userId)
                    .asFlow()
                    .map { mapper.mapSurveyInvitationToResponse(it) }

    @GetMapping("/pending")
    fun findSurveyInvitationsPage(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                  @RequestParam size: Int,
                                  @RequestParam page: Int): Flux<SurveyInvitationGridResponse> =
            service.findSurveyInvitationsPage(principal.userId, page, size)
                    .flatMap(mapper::mapSurveyInvitationToGridResponse)

    @GetMapping("/pending/count")
    fun countSurveyInvitations(@AuthenticationPrincipal principal: JwtAuthenticationToken) =
            service.countAllByUserId(principal.userId)

}
