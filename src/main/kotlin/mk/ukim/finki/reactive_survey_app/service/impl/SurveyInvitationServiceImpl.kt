package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import mk.ukim.finki.reactive_survey_app.repository.SurveyInvitationRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class SurveyInvitationServiceImpl(
    private val repository: SurveyInvitationRepository
) : SurveyInvitationService {

    override suspend fun createInvitation(survey: Survey, creator: Long, userId: Long): SurveyInvitation {
        if (creator != survey.createdBy)
            throw AccessDeniedException("You cannot send invitation for a survey that you didn't create!")
        return repository.save(
            SurveyInvitation(
                id = null,
                surveyId = survey.id!!,
                userId = userId,
                taken = false
            )
        )
    }

    override fun findInvitationsBySurvey(survey: Survey, initiatedBy: Long): Flow<SurveyInvitation> {
        if (survey.createdBy != initiatedBy)
            throw AccessDeniedException("You cannot view invitations for a survey that you didn't create!")
        return repository.findAllBySurveyId(survey.id!!)
    }

    override fun findInvitationsBySurveyNaturalKey(naturalKey: String): Flow<SurveyInvitation> =
        repository.findAllBySurveyNaturalKey(naturalKey)

    override fun findInvitationsBySurveyId(surveyId: Long): Flow<SurveyInvitation> =
        repository.findAllBySurveyId(surveyId)

    override fun findSurveyInvitationsPage(userId: Long, page: Int, size: Int): Flow<SurveyInvitation> =
        repository.findAllByUserIdAndTaken(userId, false, PageRequest.of(page, size))

    override suspend fun countAllByUserId(userId: Long): Int = repository.countAllByUserId(userId)

    override suspend fun markAsTaken(surveyId: Long, userId: Long): Int = repository.markAsTaken(surveyId, userId)

}
