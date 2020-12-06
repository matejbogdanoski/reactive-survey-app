package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyService {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
    fun findById(id: Long, initiatedBy: Mono<User>): Mono<Survey>
    fun findById(id: Long): Mono<Survey>
    fun createSurvey(createdBy: Long): Mono<Survey>
    fun updateSurvey(id: Long, title: String?, description: String?, canTakeAnonymously: Boolean?): Mono<Survey>
    fun findAllCreatedByPage(createdBy: Long, page: Int, size: Int): Flux<Survey>
    fun countAllCreatedBy(createdBy: Long): Mono<Int>
}
