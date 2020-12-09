package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<Survey> = repository.findOneByNaturalKey(naturalKey)

    override fun findById(id: Long, initiatedBy: Long): Mono<Survey> = repository.findById(id).let { surveyMono ->
        AccessValidator.validateCanViewSurvey(surveyMono, initiatedBy)
    }

    override fun findById(id: Long): Mono<Survey> = repository.findById(id)

    override fun findAllCreatedByPage(createdBy: Long, page: Int, size: Int): Flux<Survey> =
            repository.findAllByCreatedBy(createdBy, PageRequest.of(page, size))

    override fun countAllCreatedBy(createdBy: Long): Mono<Int> = repository.countAllByCreatedBy(createdBy)

    override fun createSurvey(createdBy: Long): Mono<Survey> = repository.save(
            Survey(id = null,
                   title = "Untitled survey",
                   description = null,
                   naturalKey = null,
                   createdBy = createdBy)).flatMap { findById(it.id!!) }

    override fun updateSurvey(id: Long, title: String?, description: String?,
                              canTakeAnonymously: Boolean?): Mono<Survey> = repository.findById(id).flatMap {
        repository.save(it.copy(id = it.id,
                                title = title ?: it.title,
                                description = description ?: it.description,
                                naturalKey = it.naturalKey))
    }

}
