package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionRepository
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository,
        private val questionRepository: SurveyQuestionRepository
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<Survey> = repository.findOneByNaturalKey(naturalKey)

    override fun createSurvey(): Mono<Survey> {
//        val question = questionRepository.save(
//                SurveyQuestion(id = null,
//                               surveyId = -1,
//                               questionTypeId = 0,
//                               name = "Untitled question",
//                               position = 1,
//                               isRequired = false)
//        )
        return repository.save(
                Survey(id = null,
                       title = "Untitled survey",
                       description = null,
                       naturalKey = UUID.randomUUID().toString(),
                       canTakeAnonymously = true)
        )
    }
}
