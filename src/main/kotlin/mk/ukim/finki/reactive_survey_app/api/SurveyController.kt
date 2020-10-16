package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.responses.SelectOptionResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val surveyService: SurveyService
) {

    @GetMapping("/question-types")
    fun findAllQuestionTypesAsSelectOptions(): Flux<SelectOptionResponse> = surveyService.findAllQuestionTypesAsSelectOptions()
}
