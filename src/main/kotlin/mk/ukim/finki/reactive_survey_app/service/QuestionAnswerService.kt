package mk.ukim.finki.reactive_survey_app.service

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface QuestionAnswerService {
    suspend fun createQuestionAnswer(surveyQuestionId: Long, answer: String?, surveyInstanceId: Long): QuestionAnswer
    fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, String?>, surveyInstanceId: Long): Flow<QuestionAnswer>
    fun findAllAnswersByQuestionId(questionId: Long): Flow<QuestionAnswer>
    fun findAllAnswersByInstanceId(instanceId: Long): Flow<QuestionAnswer>
}
