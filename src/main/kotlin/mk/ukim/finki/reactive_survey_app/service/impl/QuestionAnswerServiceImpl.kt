package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.repository.QuestionAnswerRepository
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.stereotype.Service

@Service
class QuestionAnswerServiceImpl(
        private val repository: QuestionAnswerRepository
) : QuestionAnswerService {

    override suspend fun createQuestionAnswer(surveyQuestionId: Long, answer: String?,
                                              surveyInstanceId: Long): QuestionAnswer = repository.save(
            QuestionAnswer(id = null,
                    surveyQuestionId = surveyQuestionId,
                    answer = answer,
                    surveyInstanceId = surveyInstanceId)
    )

    override fun bulkCreateQuestionAnswers(questionAnswerMap: Map<Long, String?>,
                                           surveyInstanceId: Long): Flow<QuestionAnswer> {
        val answers = questionAnswerMap.entries.map {
            QuestionAnswer(id = null,
                    surveyQuestionId = it.key,
                    answer = it.value,
                    surveyInstanceId = surveyInstanceId)
        }
        return repository.saveAll(answers)
    }

    override fun findAllAnswersByQuestionId(questionId: Long): Flow<QuestionAnswer> =
            repository.findAllBySurveyQuestionId(questionId)

    override fun findAllAnswersByInstanceId(instanceId: Long): Flow<QuestionAnswer> =
            repository.findAllBySurveyInstanceId(instanceId)

}
