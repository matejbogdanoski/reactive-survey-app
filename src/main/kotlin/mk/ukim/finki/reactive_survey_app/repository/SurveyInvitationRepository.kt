package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SurveyInvitationRepository : ReactiveCrudRepository<SurveyInvitation, Long> {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyInvitation>
    fun findAllByUserIdAndTaken(userId: Long, taken: Boolean, pageable: Pageable): Flux<SurveyInvitation>
}
