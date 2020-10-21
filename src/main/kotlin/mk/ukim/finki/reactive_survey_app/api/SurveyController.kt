package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
        private val surveyService: SurveyService
) {
}
