package mk.ukim.finki.reactive_survey_app.repository

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyInstanceRepository : CoroutineCrudRepository<SurveyInstance, Long> {
    fun findAllBySurveyId(surveyId: Long): Flow<SurveyInstance>

    @Query("select count(*) from survey.survey_instances where survey_id = :surveyId")
    suspend fun countBySurveyId(surveyId: Long): Int

    fun findAllByTakenBy(id: Long, pageable: Pageable): Flow<SurveyInstance>
    suspend fun countByTakenBy(takenBy: Long): Int
}
