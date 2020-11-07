package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SurveyQuestionOptionRepository : ReactiveCrudRepository<SurveyQuestionOption, Long> {
    fun findAllBySurveyQuestionId(surveyQuestionId: Long, sort: Sort): Flux<SurveyQuestionOption>

    @Query("select coalesce(max(position), 0) from survey.survey_question_options where survey_question_id = :surveyQuestionId")
    fun findMaxPosition(surveyQuestionId: Long): Mono<Int>

    @Modifying
    @Query("update survey.survey_question_options set label = :newLabel where id = :questionOptionId")
    fun updateQuestionOptionLabel(questionOptionId: Long, newLabel: String): Mono<Int>

    @Modifying
    @Query("update survey.survey_question_options set position = :position where id = :questionOptionId")
    fun updatePositionForQuestionOption(questionOptionId: Long, position: Int): Mono<Int>
}
