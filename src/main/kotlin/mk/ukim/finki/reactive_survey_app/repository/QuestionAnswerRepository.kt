package mk.ukim.finki.reactive_survey_app.repository

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface QuestionAnswerRepository : CoroutineCrudRepository<QuestionAnswer, Long> {
    fun findAllBySurveyQuestionId(questionId: Long): Flow<QuestionAnswer>
    fun findAllBySurveyInstanceId(instanceId: Long): Flow<QuestionAnswer>
}
