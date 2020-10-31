package mk.ukim.finki.reactive_survey_app.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyInstanceRepository : ReactiveCrudRepository<SurveyInstanceRepository, Long>
