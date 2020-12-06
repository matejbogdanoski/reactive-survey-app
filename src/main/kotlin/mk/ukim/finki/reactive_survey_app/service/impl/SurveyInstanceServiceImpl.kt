package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.SurveyInstanceRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import mk.ukim.finki.reactive_survey_app.validators.AccessValidator
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

@Service
class SurveyInstanceServiceImpl(
        private val repository: SurveyInstanceRepository
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

    override fun findById(surveyInstanceId: Long, initiatedBy: Mono<User>): Mono<SurveyInstance> =
            repository.findById(surveyInstanceId).let { surveyInstanceMono ->
                AccessValidator.validateCanViewSurveyInstance(surveyInstanceMono, initiatedBy).map { it.t1 }
            }

    override fun findAllTakenByPage(takenBy: Long, size: Int, page: Int): Flux<SurveyInstance> =
            repository.findAllByTakenBy(takenBy, PageRequest.of(page, size))

    override fun countAllTakenBy(takenBy: Long): Mono<Int> = repository.countByTakenBy(takenBy)
}
