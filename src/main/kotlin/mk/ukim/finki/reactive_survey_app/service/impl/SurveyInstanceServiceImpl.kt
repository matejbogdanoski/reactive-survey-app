package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.repository.SurveyInstanceRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import mk.ukim.finki.reactive_survey_app.service.UserService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

@Service
class SurveyInstanceServiceImpl(
        private val repository: SurveyInstanceRepository,
        private val userService: UserService
) : SurveyInstanceService {

    override fun create(surveyId: Long, takenBy: Long,
                        dateTaken: ZonedDateTime): Mono<SurveyInstance> = repository.save(
            SurveyInstance(
                    id = null,
                    takenBy = takenBy,
                    surveyId = surveyId,
                    dateTaken = ZonedDateTime.now())
    )

    override fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstance> = repository.findAllBySurveyId(surveyId)

    override fun countAllBySurveyId(surveyId: Long): Mono<Int> = repository.countBySurveyId(surveyId)

    override fun findById(surveyInstanceId: Long): Mono<SurveyInstance> = repository.findById(surveyInstanceId)

    override fun findById(surveyInstanceId: Long, initiatedBy: String): Mono<SurveyInstance> {
        val surveyInstance = repository.findById(surveyInstanceId)
        val user = userService.findByUsername(initiatedBy)
        return AccessValidator.validateCanViewSurveyInstance(surveyInstance, user).then(surveyInstance)
    }

    override fun findAllTakenByPage(takenBy: Long, size: Int, page: Int): Flux<SurveyInstance> =
            repository.findAllByTakenBy(takenBy, PageRequest.of(page, size))

    override fun countAllTakenBy(takenBy: Long): Mono<Int> = repository.countByTakenBy(takenBy)
}
