package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import reactor.core.publisher.Flux


interface SurveyQuestionService {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyQuestion>
}
