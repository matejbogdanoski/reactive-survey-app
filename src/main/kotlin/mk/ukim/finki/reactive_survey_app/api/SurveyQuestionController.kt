package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/surveys/questions")
class SurveyQuestionController(
        private val service: SurveyQuestionService
) {
}
