package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyInvitationRepository : ReactiveCrudRepository<SurveyInvitation, Long>
