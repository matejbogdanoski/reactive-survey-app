package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import reactor.core.publisher.Mono

interface SurveyService {
    fun findOneByNaturalKey(naturalKey: String): Mono<SurveyResponse>
    fun createSurvey(): Mono<SurveyResponse>
}
