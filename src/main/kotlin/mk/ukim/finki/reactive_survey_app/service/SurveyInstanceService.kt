package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

interface SurveyInstanceService {
    fun create(surveyId: Long, takenBy: Long, dateTaken: ZonedDateTime): Mono<SurveyInstance>
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstance>
    fun countAllBySurveyId(surveyId: Long): Mono<Int>
    fun findById(surveyInstanceId: Long): Mono<SurveyInstance>
    fun findById(surveyInstanceId: Long, initiatedBy: Long): Mono<SurveyInstance>
    fun findAllTakenByPage(takenBy: Long, size: Int, page: Int): Flux<SurveyInstance>
    fun countAllTakenBy(takenBy: Long): Mono<Int>
}
