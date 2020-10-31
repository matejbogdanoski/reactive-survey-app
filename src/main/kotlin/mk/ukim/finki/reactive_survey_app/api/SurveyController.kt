package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyMapper
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val service: SurveyService,
        private val mapper: SurveyMapper
) {

    @GetMapping("/natural-key/{naturalKey}")
    fun findOneByNaturalKey(@PathVariable naturalKey: String): Mono<SurveyResponse> =
            service.findOneByNaturalKey(naturalKey).flatMap(mapper::mapSurveyToResponse)

    @PostMapping
    fun createSurvey(): Mono<SurveyResponse> = service.createSurvey().flatMap(mapper::mapSurveyToResponse)

}
