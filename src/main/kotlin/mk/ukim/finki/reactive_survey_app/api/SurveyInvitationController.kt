package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInvitationMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyInvitationRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyInvitationResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInvitationGridResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/survey-invitations")
class SurveyInvitationController(
        private val service: SurveyInvitationManagingService,
        private val mapper: SurveyInvitationMapper
) {

    @PostMapping
    fun createSurveyInvitation(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                               @RequestBody request: SurveyInvitationRequest): Mono<SurveyInvitation> =
            with(request) { service.createSurveyInvitation(principal.username!!, surveyId, username) }

    @GetMapping("/{surveyId}")
    fun findInvitationsBySurvey(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                @PathVariable surveyId: Long): Flux<SurveyInvitationResponse> =
            service.findInvitationsBySurvey(surveyId, principal.username!!)
                    .flatMap(mapper::mapSurveyInvitationToResponse)

    @GetMapping("/pending")
    fun findSurveyInvitationsPage(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                  @RequestParam size: Int,
                                  @RequestParam page: Int): Flux<SurveyInvitationGridResponse> =
            service.findSurveyInvitationPage(principal.username!!, page, size)
                    .flatMap(mapper::mapSurveyInvitationToGridResponse)

    @GetMapping("/pending/count")
    fun countSurveyInvitations(@AuthenticationPrincipal principal: JwtAuthenticationToken) =
            service.countAllSurveyInvitations(principal.username!!)

}
