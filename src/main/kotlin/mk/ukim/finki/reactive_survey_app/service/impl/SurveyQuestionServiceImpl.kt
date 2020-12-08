package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionRepository
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyQuestionServiceImpl(
        private val repository: SurveyQuestionRepository
) : SurveyQuestionService {

    override fun findAllBySurveyId(surveyId: Long): Flux<SurveyQuestion> =
            repository.findAllBySurveyId(surveyId, Sort.by("position"))

    override fun createSurveyQuestion(surveyId: Long): Mono<SurveyQuestion> = repository.findMaxPosition(surveyId)
            .flatMap {
                repository.save(
                        SurveyQuestion(id = null,
                                       surveyId = surveyId,
                                       questionTypeId = 0,
                                       name = "Untitled question",
                                       position = it.plus(1),
                                       isRequired = false)
                )
            }

    override fun deleteSurveyQuestion(surveyQuestionId: Long): Mono<Void> = repository.deleteById(surveyQuestionId)

    override fun editSurveyQuestionInfo(surveyQuestionId: Long,
                                        request: SurveyQuestionUpdateRequest): Mono<SurveyQuestion> =
            repository.findById(surveyQuestionId).flatMap {
                repository.save(SurveyQuestion(id = it.id,
                                               surveyId = it.surveyId,
                                               questionTypeId = request.questionType?.let { type ->
                                                   QuestionType.valueOf(type).ordinal.toLong()
                                               } ?: it.questionTypeId,
                                               name = request.name ?: it.name,
                                               position = it.position,
                                               isRequired = request.isRequired ?: it.isRequired))
            }

    override fun duplicateSurveyQuestion(surveyId: Long, surveyQuestionId: Long): Mono<SurveyQuestion> =
            repository.findById(surveyQuestionId)
                    .flatMap { repository.save(it.copy(id = null, position = it.position.plus(1))) }
                    .doOnSuccess { updatePositions(surveyId, it.position, it.id!!).subscribe() }

    override fun updateSurveyQuestionPosition(surveyQuestionId: Long, newPosition: Int): Mono<SurveyQuestion> =
            repository.updatePositionForQuestion(surveyQuestionId, newPosition).flatMap {
                repository.findById(surveyQuestionId)
            }


    override fun findById(questionId: Long): Mono<SurveyQuestion> = repository.findById(questionId)

    private fun updatePositions(surveyId: Long, position: Int, surveyQuestionId: Long): Flux<SurveyQuestion> =
            repository.findAllToIncrementPosition(surveyId, position, surveyQuestionId).flatMap {
                repository.save(it.copy(position = it.position.plus(1)))
            }

}
