package mk.ukim.finki.reactive_survey_app.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyInstanceRepository : R2dbcRepository<SurveyInstanceRepository, Long>
