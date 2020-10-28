package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SurveyQuestionOptionRepository : R2dbcRepository<SurveyQuestionOption, Long> {

    fun findAllBySurveyQuestionIdOrderByPosition(surveyQuestionId: Long): Flux<SurveyQuestionOption>
}
