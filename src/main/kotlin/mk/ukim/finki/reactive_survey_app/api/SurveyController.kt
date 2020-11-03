package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.requests.SurveyUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val service: SurveyService
) {

    @GetMapping("/natural-key/{naturalKey}")
    fun findByNaturalKey(@PathVariable naturalKey: String): Mono<Survey> = service.findOneByNaturalKey(naturalKey)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Mono<Survey> = service.findById(id)

    @PostMapping
    fun createSurvey(): Mono<Survey> = service.createSurvey()

    @PatchMapping("/{surveyId}")
    fun updateSurvey(@PathVariable surveyId: Long, @RequestBody request: SurveyUpdateRequest): Mono<Survey> = with(
            request) { service.updateSurvey(surveyId, title, description, canTakeAnonymously) }
}
