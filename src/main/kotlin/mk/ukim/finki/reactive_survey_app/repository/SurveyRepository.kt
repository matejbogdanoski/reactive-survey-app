package mk.ukim.finki.reactive_survey_app.repository

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.Survey
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : CoroutineCrudRepository<Survey, Long> {
    suspend fun findOneByNaturalKey(naturalKey: String): Survey?
    fun findAllByCreatedBy(userId: Long, pageable: Pageable): Flow<Survey>
    suspend fun countAllByCreatedBy(userId: Long): Int
}
