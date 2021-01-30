package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
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

    override fun findAllBySurveyQuestionId(surveyQuestionId: Long): Flow<SurveyQuestionOption> =
            repository.findAllBySurveyQuestionId(surveyQuestionId, Sort.by("position"))

    override suspend fun createSurveyQuestionOption(surveyQuestionId: Long): SurveyQuestionOption =
            repository.findMaxPosition(surveyQuestionId).let {
                val newPosition = it.plus(1)
                repository.save(
                        SurveyQuestionOption(id = null,
                                surveyQuestionId = surveyQuestionId,
                                label = "Option $newPosition",
                                position = newPosition)
                )
            }

    override suspend fun updateQuestionOptionLabel(surveyQuestionId: Long, optionId: Long,
                                                   newLabel: String): SurveyQuestionOption {
        repository.updateQuestionOptionLabel(optionId, newLabel)
        return repository.findById(optionId)!!
    }

    override suspend fun updateQuestionOptionPosition(surveyQuestionId: Long, optionId: Long,
                                                      newPosition: Int): SurveyQuestionOption {
        repository.updatePositionForQuestionOption(optionId, newPosition)
        return repository.findById(optionId)!!
    }

    override suspend fun deleteQuestionOption(surveyQuestionId: Long, optionId: Long) = repository.deleteById(optionId)

    override fun duplicateAllBySurveyQuestionId(fromSurveyQuestionId: Long,
                                                toSurveyQuestionId: Long): Flow<SurveyQuestionOption> {
        return repository.findAllBySurveyQuestionId(fromSurveyQuestionId).map {
            repository.save(it.copy(id = null, surveyQuestionId = toSurveyQuestionId))
        }
    }

}
