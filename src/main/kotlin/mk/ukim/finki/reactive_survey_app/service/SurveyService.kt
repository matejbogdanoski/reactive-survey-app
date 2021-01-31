package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.Survey

interface SurveyService {
    suspend fun findOneByNaturalKey(naturalKey: String): Survey
    suspend fun findById(id: Long, initiatedBy: Long): Survey
    suspend fun findById(id: Long): Survey
    suspend fun createSurvey(createdBy: Long): Survey
    suspend fun updateSurvey(id: Long, title: String?, description: String?, canTakeAnonymously: Boolean?): Survey
    fun findAllCreatedByPage(createdBy: Long, page: Int, size: Int): Flow<Survey>
    suspend fun countAllCreatedBy(createdBy: Long): Int
}
