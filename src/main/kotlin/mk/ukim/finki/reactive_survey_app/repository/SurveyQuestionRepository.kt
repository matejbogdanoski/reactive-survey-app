package mk.ukim.finki.reactive_survey_app.repository

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyQuestionRepository : CoroutineCrudRepository<SurveyQuestion, Long> {
    fun findAllBySurveyId(surveyId: Long, sort: Sort): Flow<SurveyQuestion>

    @Query("select * from survey.survey_questions where " +
            "survey_id = :surveyId and position >= :position and id != :surveyQuestionId")
    fun findAllToIncrementPosition(surveyId: Long, position: Int, surveyQuestionId: Long): Flow<SurveyQuestion>

    @Query("select coalesce(max(position), 0) from survey.survey_questions where survey_id = :surveyId")
    suspend fun findMaxPosition(surveyId: Long): Int

    @Modifying
    @Query("update survey.survey_questions set position = :position where id = :surveyQuestionId")
    suspend fun updatePositionForQuestion(surveyQuestionId: Long, position: Int): Int // returns number of modified rows

}
