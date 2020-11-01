package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyQuestionServiceImpl(
        private val repository: SurveyQuestionRepository
) : SurveyQuestionService {

    override fun findAllBySurveyId(
            surveyId: Long): Flux<SurveyQuestion> = repository.findAllBySurveyId(surveyId, Sort.by("position"))

    override fun createSurveyQuestion(surveyId: Long): Mono<SurveyQuestion> = repository.findMaxPosition(
            surveyId).flatMap {
        repository.save(
                SurveyQuestion(id = null,
                               surveyId = surveyId,
                               questionTypeId = 0,
                               name = "Untitled question",
                               position = it + 1,
                               isRequired = false)
        )
    }
}
