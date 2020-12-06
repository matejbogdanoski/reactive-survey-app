package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.repository.SurveyInvitationRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyInvitationServiceImpl(
        private val repository: SurveyInvitationRepository
) : SurveyInvitationService {

    override fun createInvitation(surveyMono: Mono<Survey>, creator: Long, userId: Long): Mono<SurveyInvitation> =
            AccessValidator.validateCanCreateSurveyInvitation(creator, surveyMono).flatMap {
                repository.save(SurveyInvitation(id = null,
                                                 surveyId = it.id!!,
                                                 userId = userId,
                                                 taken = false))
            }

    override fun findInvitationsBySurvey(surveyMono: Mono<Survey>, initiatedBy: Long): Flux<SurveyInvitation> =
            AccessValidator.validateCanViewSurveyInvitations(surveyMono, initiatedBy)
                    .flatMapMany { repository.findAllBySurveyId(it.id!!) }

    override fun findSurveyInvitationsPage(userId: Long, page: Int, size: Int): Flux<SurveyInvitation> =
            repository.findAllByUserIdAndTaken(userId, false, PageRequest.of(page, size))

    override fun countAllByUserId(userId: Long): Mono<Int> = repository.countAllByUserId(userId)

}
