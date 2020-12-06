package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInstanceMapper
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstancePreview
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstanceResponse
import mk.ukim.finki.reactive_survey_app.responses.grid.SurveyInstanceGridResponse
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/survey-instances")
class SurveyInstanceController(
        private val service: SurveyInstanceManagingService,
        private val mapper: SurveyInstanceMapper
) {

    @GetMapping("/stream-answers/{surveyId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getAnswerStream(@PathVariable surveyId: Long): Flux<AnswerDTO?> = service.streamInstanceAnswers(surveyId)

    @GetMapping("/{surveyId}")
    fun findAllBySurvey(@PathVariable surveyId: Long): Flux<SurveyInstanceResponse> =
            service.findAllBySurveyId(surveyId).flatMap(mapper::mapSurveyInstanceToResponse)

    @GetMapping("/by-user")
    fun findAllTakenSurveysPage(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                @RequestParam size: Int,
                                @RequestParam page: Int): Flux<SurveyInstanceGridResponse> =
            service.findAllTakenByPage(principal.username!!, size, page)
                    .flatMap(mapper::mapSurveyInstanceToGridResponse)

    @GetMapping("/count-by-user")
    fun countAllTakenBy(@AuthenticationPrincipal principal: JwtAuthenticationToken) =
            service.countAllByUsername(principal.username!!)

    @GetMapping("/preview/{instanceId}")
    fun findInstanceById(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                         @PathVariable instanceId: Long): Mono<SurveyInstancePreview> =
            service.findById(instanceId, principal.username!!).flatMap(mapper::mapSurveyInstanceToPreviewResponse)

    //todo: can submit answers if the user is invited and hasn't taken the survey yet and is not the creator
    //todo: mark invitation as taken on instance creation
    @PostMapping("/{surveyId}")
    fun createInstanceWithAnswers(@RequestBody questionAnswerMap: Map<Long, String?>,
                                  @PathVariable surveyId: Long,
                                  @AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<SurveyInstance> =
            service.createInstanceWithAnswers(questionAnswerMap, surveyId, principal.username!!)

}
