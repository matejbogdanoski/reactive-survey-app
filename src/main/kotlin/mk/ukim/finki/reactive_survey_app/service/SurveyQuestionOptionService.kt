package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import reactor.core.publisher.Flux

interface SurveyQuestionOptionService {
    fun findAllBySurveyQuestionId(surveyQuestionId: Long): Flux<SurveyQuestionOption>
}
