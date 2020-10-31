package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<Survey> = repository.findOneByNaturalKey(naturalKey)

    override fun createSurvey(): Mono<Survey> =
            repository.save(
                    Survey(id = null,
                           title = "Untitled survey",
                           description = null,
                           naturalKey = UUID.randomUUID().toString(),
                           canTakeAnonymously = true))
}
