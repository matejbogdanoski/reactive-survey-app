package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.Survey

interface SurveyManagingService {
    suspend fun createSurveyWithQuestion(createdBy: Long): Survey
}
