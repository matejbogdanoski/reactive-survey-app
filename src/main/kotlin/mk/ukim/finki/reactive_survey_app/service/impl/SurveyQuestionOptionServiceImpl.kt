package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionOptionRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyQuestionOptionServiceImpl(
        private val repository: SurveyQuestionOptionRepository
) : SurveyQuestionOptionService {

    override fun findAllBySurveyQuestionId(
            surveyQuestionId: Long): Flux<SurveyQuestionOption> = repository.findAllBySurveyQuestionId(surveyQuestionId,
                                                                                                       Sort.by("position"))

    override fun createSurveyQuestionOption(surveyQuestionId: Long): Mono<SurveyQuestionOption> =
            repository.findMaxPosition(surveyQuestionId).flatMap {
                repository.save(
                        SurveyQuestionOption(id = null,
                                             surveyQuestionId = surveyQuestionId,
                                             label = "Option ${it.plus(1)}",
                                             position = it.plus(1))
                )
            }

    override fun updateQuestionOptionLabel(surveyQuestionId: Long, optionId: Long,
                                           newLabel: String): Mono<SurveyQuestionOption> =
            repository.updateQuestionOptionLabel(optionId, newLabel).flatMap {
                repository.findById(optionId)
            }

    override fun updateQuestionOptionPosition(surveyQuestionId: Long, optionId: Long,
                                              newPosition: Int): Mono<SurveyQuestionOption> =
            repository.updatePositionForQuestionOption(optionId, newPosition).flatMap {
                repository.findById(optionId)
            }

    override fun deleteQuestionOption(surveyQuestionId: Long, optionId: Long): Mono<Void> = repository.deleteById(
            optionId)

    override fun duplicateAllBySurveyQuestionId(fromSurveyQuestionId: Long,
                                                toSurveyQuestionId: Long): Flux<SurveyQuestionOption> =
            repository.findAllBySurveyQuestionId(fromSurveyQuestionId).flatMap {
                repository.save(it.copy(id = null, surveyQuestionId = toSurveyQuestionId))
            }
}
