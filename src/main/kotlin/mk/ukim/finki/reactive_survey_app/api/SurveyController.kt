package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val service: SurveyService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long,
                 @AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<Survey> =
            service.findById(id, principal.userId)

    @GetMapping("/my-surveys")
    fun findAllSurveysByUserPageable(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                     @RequestParam size: Int,
                                     @RequestParam page: Int): Flux<SurveyResponse> =
            service.findAllCreatedByPage(principal.userId, page, size)
                    .map(SurveyStaticMapper::mapSurveyToResponseStatic)

    @GetMapping("/my-surveys/count")
    fun countAllSurveysByUser(@AuthenticationPrincipal principal: JwtAuthenticationToken) =
            service.countAllCreatedBy(principal.userId)

    @PostMapping
    fun createSurvey(@AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<Survey> =
            service.createSurvey(principal.userId)

    @PatchMapping("/{surveyId}")
    fun updateSurvey(@PathVariable surveyId: Long, @RequestBody request: SurveyUpdateRequest): Mono<Survey> = with(
            request) { service.updateSurvey(surveyId, title, description, canTakeAnonymously) }
}
