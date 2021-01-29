package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {

    override suspend fun findOneByNaturalKey(naturalKey: String): Survey =
            repository.findOneByNaturalKey(naturalKey)
                    ?: throw NoSuchElementException("Survey with natural key $naturalKey does not exist!")

    override suspend fun findById(id: Long, initiatedBy: Long): Survey =
            repository.findById(id)?.let { AccessValidator.validateCanViewSurvey(it, initiatedBy) }
                    ?: throw NoSuchElementException("Survey with id $id does not exist!")

    override suspend fun findById(id: Long): Survey = repository.findById(id)
            ?: throw NoSuchElementException("Survey with id $id does not exist!")

    override fun findAllCreatedByPage(createdBy: Long, page: Int, size: Int): Flow<Survey> =
            repository.findAllByCreatedBy(createdBy, PageRequest.of(page, size))

    override suspend fun countAllCreatedBy(createdBy: Long): Int = repository.countAllByCreatedBy(createdBy)

    override suspend fun createSurvey(createdBy: Long): Survey = repository.save(
            Survey(id = null,
                    title = "Untitled survey",
                    description = null,
                    naturalKey = null,
                    createdBy = createdBy))

    override suspend fun updateSurvey(id: Long, title: String?, description: String?,
                                      canTakeAnonymously: Boolean?): Survey = repository.findById(id)?.let {
        repository.save(it.copy(id = it.id,
                title = title ?: it.title,
                description = description ?: it.description,
                naturalKey = it.naturalKey))
    } ?: throw IllegalStateException("Survey with id $id does not exist!")

}
