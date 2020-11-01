package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import reactor.core.publisher.Mono

interface SurveyService {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
    fun findById(id: Long): Mono<Survey>
    fun createSurvey(): Mono<Survey>
    fun updateSurvey(id: Long, title: String?, description: String?, canTakeAnonymously: Boolean?): Mono<Survey>
}
