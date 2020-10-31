package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.Survey
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SurveyRepository : ReactiveCrudRepository<Survey, Long> {
    fun findOneByNaturalKey(naturalKey: String): Mono<Survey>
}
