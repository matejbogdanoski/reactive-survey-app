package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository
) : SurveyService {
}
