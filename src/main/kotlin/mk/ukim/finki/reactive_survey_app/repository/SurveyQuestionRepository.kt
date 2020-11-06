package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SurveyQuestionRepository : ReactiveCrudRepository<SurveyQuestion, Long> {
    fun findAllBySurveyId(surveyId: Long, sort: Sort): Flux<SurveyQuestion>

    @Query("select * from survey.survey_questions where " +
                   "survey_id = :surveyId and position >= :position and id != :surveyQuestionId")
    fun findAllToIncrementPosition(surveyId: Long, position: Int, surveyQuestionId: Long): Flux<SurveyQuestion>

    @Query("select coalesce(max(position), 0) from survey.survey_questions where survey_id = :surveyId")
    fun findMaxPosition(surveyId: Long): Mono<Int>

    @Modifying
    @Query("update survey.survey_questions set position = :position where id = :surveyQuestionId")
    fun updatePositionForQuestion(surveyQuestionId: Long, position: Int): Mono<Int> // returns number of modified rows

}
