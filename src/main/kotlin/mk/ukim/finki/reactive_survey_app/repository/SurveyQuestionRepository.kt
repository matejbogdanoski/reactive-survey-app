package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import org.springframework.data.domain.Sort
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SurveyQuestionRepository : ReactiveCrudRepository<SurveyQuestion, Long> {
    fun findAllBySurveyId(surveyId: Long, sort: Sort): Flux<SurveyQuestion>
}
