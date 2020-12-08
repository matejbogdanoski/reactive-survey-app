package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import reactor.core.publisher.Mono

interface SurveyQuestionManagingService {
    fun duplicateQuestion(surveyId: Long, surveyQuestionId: Long): Mono<SurveyQuestion>
}
