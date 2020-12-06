package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val service: SurveyService,
        private val managing: SurveyManagingService
) {

    @GetMapping("/natural-key/{naturalKey}")
    fun findByNaturalKey(@PathVariable naturalKey: String): Mono<Survey> = service.findOneByNaturalKey(naturalKey)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long,
                 @AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<Survey> =
            managing.findById(id, principal.username!!)

    @GetMapping("/my-surveys")
    fun findAllSurveysByUserPageable(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                     @RequestParam size: Int,
                                     @RequestParam page: Int): Flux<SurveyResponse> =
            managing.findAllByUsernamePage(principal.username!!, page, size)
                    .map(SurveyStaticMapper::mapSurveyToResponseStatic)

    @GetMapping("/my-surveys/count")
    fun countAllSurveysByUser(@AuthenticationPrincipal principal: JwtAuthenticationToken) = managing.countAllByUsername(
            principal.username!!)

    @PostMapping
    fun createSurvey(@AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<Survey> =
            managing.createSurvey(principal.username!!)

    @PatchMapping("/{surveyId}")
    fun updateSurvey(@PathVariable surveyId: Long, @RequestBody request: SurveyUpdateRequest): Mono<Survey> = with(
            request) { service.updateSurvey(surveyId, title, description, canTakeAnonymously) }
}
