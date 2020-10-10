package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class SurveyService(
        private val repository: SurveyRepository
) {

}
