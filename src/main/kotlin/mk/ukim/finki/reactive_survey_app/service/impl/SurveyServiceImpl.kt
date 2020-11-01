package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<Survey> = repository.findOneByNaturalKey(naturalKey)

    override fun findById(id: Long): Mono<Survey> = repository.findById(id)

    override fun createSurvey(): Mono<Survey> = repository.save(
            Survey(id = null,
                   title = "Untitled survey",
                   description = null,
                   naturalKey = null,
                   canTakeAnonymously = true))

    override fun updateSurvey(id: Long, title: String?, description: String?,
                              canTakeAnonymously: Boolean?): Mono<Survey> = repository.findById(id).flatMap {
        repository.save(Survey(id = it.id,
                               title = title ?: it.title,
                               description = description ?: it.description,
                               naturalKey = it.naturalKey,
                               canTakeAnonymously = canTakeAnonymously ?: it.canTakeAnonymously))
    }

}
