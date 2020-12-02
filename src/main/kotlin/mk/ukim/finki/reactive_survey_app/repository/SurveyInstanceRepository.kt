package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SurveyInstanceRepository : ReactiveCrudRepository<SurveyInstance, Long> {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstance>

    @Query("select count(*) from survey.survey_instances where survey_id = :surveyId")
    fun countBySurveyId(surveyId: Long): Mono<Int>

    fun findAllByTakenBy(id: Long, pageable: Pageable): Flux<SurveyInstance>
    fun countByTakenBy(takenBy: Long): Mono<Int>
}
