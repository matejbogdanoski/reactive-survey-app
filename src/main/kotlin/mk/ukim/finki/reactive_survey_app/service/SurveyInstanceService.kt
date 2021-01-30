package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import java.time.ZonedDateTime

interface SurveyInstanceService {
    suspend fun create(surveyId: Long, takenBy: Long, dateTaken: ZonedDateTime): SurveyInstance
    fun findAllBySurveyId(surveyId: Long): Flow<SurveyInstance>
    suspend fun countAllBySurveyId(surveyId: Long): Int
    suspend fun findById(surveyInstanceId: Long): SurveyInstance
    suspend fun findById(surveyInstanceId: Long, initiatedBy: Long): SurveyInstance
    fun findAllTakenByPage(takenBy: Long, size: Int, page: Int): Flow<SurveyInstance>
    suspend fun countAllTakenBy(takenBy: Long): Int
}
