package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SurveyQuestionServiceImpl(
        private val repository: SurveyQuestionRepository
) : SurveyQuestionService {

    override fun findAllBySurveyId(surveyId: Long): Flux<SurveyQuestion> = repository.findAllBySurveyId(surveyId)
}
