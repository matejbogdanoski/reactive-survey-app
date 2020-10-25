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
}
