package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<SurveyResponse> = repository.findOneByNaturalKey(
            naturalKey).map(SurveyStaticMapper::mapSurveyToResponseStatic)

    override fun createSurvey(): Mono<SurveyResponse> =
            repository.save(
                    Survey(id = null,
                           title = "Untitled survey",
                           description = null,
                           naturalKey = UUID.randomUUID().toString(),
                           canTakeAnonymously = true)).map(SurveyStaticMapper::mapSurveyToResponseStatic)
}
