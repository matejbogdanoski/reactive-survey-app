package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyService {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
    fun findById(id: Long, initiatedBy: String): Mono<Survey>
    fun createSurvey(createdBy: String): Mono<Survey>
    fun updateSurvey(id: Long, title: String?, description: String?, canTakeAnonymously: Boolean?): Mono<Survey>
    fun findAllByUsernamePage(username: String, page: Int, size: Int): Flux<Survey>
    fun countAllByUsername(username: String): Mono<Int>
}
