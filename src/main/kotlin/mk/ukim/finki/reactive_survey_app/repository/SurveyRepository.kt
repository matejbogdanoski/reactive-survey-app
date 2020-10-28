package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.Survey
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SurveyRepository : R2dbcRepository<Survey, Long> {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
}
