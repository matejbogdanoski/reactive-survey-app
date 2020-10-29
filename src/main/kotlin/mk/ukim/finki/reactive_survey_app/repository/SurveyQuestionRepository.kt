package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SurveyQuestionRepository : R2dbcRepository<SurveyQuestion, Long> {
    fun findAllBySurveyIdOrderByPosition(surveyId: Long): Flux<SurveyQuestion>
}
