package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.responses.SelectOptionResponse
import reactor.core.publisher.Flux

interface SurveyService {
    fun findAllQuestionTypesAsSelectOptions(): Flux<SelectOptionResponse>
}
