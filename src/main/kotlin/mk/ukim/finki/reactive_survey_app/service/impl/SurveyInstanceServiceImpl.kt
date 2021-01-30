package mk.ukim.finki.reactive_survey_app.service.impl

import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.repository.SurveyInstanceRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SurveyInstanceServiceImpl(
    private val repository: SurveyInstanceRepository
) : SurveyInstanceService {

    override suspend fun create(surveyId: Long, takenBy: Long, dateTaken: ZonedDateTime): SurveyInstance =
        repository.save(
            SurveyInstance(
                id = null,
                surveyId = surveyId,
                takenBy = takenBy,
                dateTaken = dateTaken
            )
        )

    override fun findAllBySurveyId(surveyId: Long): Flow<SurveyInstance> = repository.findAllBySurveyId(surveyId)

    override suspend fun countAllBySurveyId(surveyId: Long): Int = repository.countBySurveyId(surveyId)

    override suspend fun findById(surveyInstanceId: Long): SurveyInstance = repository.findById(surveyInstanceId)
        ?: throw NoSuchElementException("Survey instance with id $surveyInstanceId does not exist!")

    override suspend fun findById(surveyInstanceId: Long, initiatedBy: Long): SurveyInstance {
        val surveyInstance = repository.findById(surveyInstanceId)
        if (surveyInstance?.takenBy != initiatedBy)
            throw AccessDeniedException("You do not have access to this survey instance!")
        return surveyInstance
    }

    override fun findAllTakenByPage(takenBy: Long, size: Int, page: Int): Flow<SurveyInstance> =
        repository.findAllByTakenBy(takenBy, PageRequest.of(page, size))

    override suspend fun countAllTakenBy(takenBy: Long): Int = repository.countByTakenBy(takenBy)

}
