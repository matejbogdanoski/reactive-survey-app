package mk.ukim.finki.reactive_survey_app.repository

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyQuestionOptionRepository : CoroutineCrudRepository<SurveyQuestionOption, Long> {
    fun findAllBySurveyQuestionId(surveyQuestionId: Long, sort: Sort): Flow<SurveyQuestionOption>

    fun findAllBySurveyQuestionId(surveyQuestionId: Long): Flow<SurveyQuestionOption>

    @Query("select coalesce(max(position), 0) from survey.survey_question_options where survey_question_id = :surveyQuestionId")
    suspend fun findMaxPosition(surveyQuestionId: Long): Int

    @Modifying
    @Query("update survey.survey_question_options set label = :newLabel where id = :questionOptionId")
    suspend fun updateQuestionOptionLabel(questionOptionId: Long, newLabel: String): Int

    @Modifying
    @Query("update survey.survey_question_options set position = :position where id = :questionOptionId")
    suspend fun updatePositionForQuestionOption(questionOptionId: Long, position: Int): Int
}
