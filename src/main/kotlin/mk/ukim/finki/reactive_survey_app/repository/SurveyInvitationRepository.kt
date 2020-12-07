package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SurveyInvitationRepository : ReactiveCrudRepository<SurveyInvitation, Long> {
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyInvitation>
    fun findAllByUserIdAndTaken(userId: Long, taken: Boolean, pageable: Pageable): Flux<SurveyInvitation>
    fun countAllByUserId(userId: Long): Mono<Int>

    @Query("select si.* from survey.survey_invitations si\n" +
                   "join survey.surveys s on s.id = si.survey_id\n" +
                   "where s.natural_key = :naturalKey")
    fun findAllBySurveyNaturalKey(naturalKey: String): Flux<SurveyInvitation>

    @Modifying
    @Query("update survey.survey_invitations set taken = true where survey_id = :surveyId and user_id = :userId")
    fun markAsTaken(surveyId: Long, userId: Long): Mono<Int>
}
