package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import reactor.core.publisher.Mono

interface SurveyService {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
}
