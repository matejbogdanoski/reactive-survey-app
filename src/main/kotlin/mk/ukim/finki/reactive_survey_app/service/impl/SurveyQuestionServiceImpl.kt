package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SurveyQuestionServiceImpl(
        private val repository: SurveyQuestionRepository
) : SurveyQuestionService {

    override fun findAllBySurveyId(surveyId: Long): Flow<SurveyQuestion> =
            repository.findAllBySurveyId(surveyId, Sort.by("position"))

    override suspend fun createSurveyQuestion(surveyId: Long): SurveyQuestion {
        val maxPosition = repository.findMaxPosition(surveyId)
        return repository.save(
                SurveyQuestion(id = null,
                        surveyId = surveyId,
                        questionTypeId = 0,
                        name = "Untitled question",
                        position = maxPosition.plus(1),
                        isRequired = false)
        )
    }

    override suspend fun deleteSurveyQuestion(surveyQuestionId: Long): Unit = repository.deleteById(surveyQuestionId)


    override suspend fun editSurveyQuestionInfo(surveyQuestionId: Long, questionType: String?,
                                                name: String?, isRequired: Boolean?): SurveyQuestion {
        val surveyQuestion = repository.findById(surveyQuestionId)
                ?: throw NoSuchElementException("Survey question with id $surveyQuestionId does not exist!")
        val surveyQuestionUpdated = surveyQuestion.copy(
                questionTypeId = questionType?.let { QuestionType.valueOf(it).ordinal.toLong() }
                        ?: surveyQuestion.questionTypeId,
                name = name ?: surveyQuestion.name,
                isRequired = isRequired ?: surveyQuestion.isRequired
        )
        return repository.save(surveyQuestionUpdated)
    }

    override suspend fun duplicateSurveyQuestion(surveyId: Long, surveyQuestionId: Long): SurveyQuestion {
        val surveyQuestion = repository.findById(surveyQuestionId)
                ?: throw NoSuchElementException("Survey question with id $surveyQuestionId does not exist")
        val newQuestion = repository.save(surveyQuestion.copy(id = null, position = surveyQuestion.position.plus(1)))
        updatePositions(surveyId = surveyId,
                position = newQuestion.position,
                surveyQuestionId = newQuestion.id!!)
        return newQuestion
    }

    override suspend fun updateSurveyQuestionPosition(surveyQuestionId: Long, newPosition: Int): SurveyQuestion =
            repository.updatePositionForQuestion(surveyQuestionId, newPosition)
                    .let { repository.findById(surveyQuestionId)!! }


    override suspend fun findById(questionId: Long): SurveyQuestion = repository.findById(questionId)
            ?: throw NoSuchElementException("Survey question with id $questionId does not exist")

    private fun updatePositions(surveyId: Long, position: Int, surveyQuestionId: Long): Flow<SurveyQuestion> =
            repository.findAllToIncrementPosition(surveyId, position, surveyQuestionId)
                    .map { repository.save(it.copy(position = it.position.plus(1))) }

}
