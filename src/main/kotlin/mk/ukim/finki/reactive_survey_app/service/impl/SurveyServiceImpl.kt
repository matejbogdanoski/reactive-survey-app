package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.User
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyServiceImpl(
        private val repository: SurveyRepository,
        private val userService: UserService
) : SurveyService {

    override fun findOneByNaturalKey(naturalKey: String): Mono<Survey> = repository.findOneByNaturalKey(naturalKey)

    override fun findById(id: Long): Mono<Survey> = repository.findById(id)

    override fun findAllByUsernamePage(username: String, page: Int,
                                       size: Int): Flux<Survey> = userService.findByUsername(username).flatMapMany {
        repository.findAllByCreatedBy(it.id!!, PageRequest.of(page, size))
    }

    override fun countAllByUsername(username: String): Mono<Int> = userService.findByUsername(username)
            .flatMap { repository.countAllByCreatedBy(it.id!!) }

    override fun createSurvey(createdBy: String): Mono<Survey> = userService.findByUsername(createdBy).flatMap {
        repository.save(
                Survey(id = null,
                       title = "Untitled survey",
                       description = null,
                       naturalKey = null,
                       createdBy = it.id))

    }

    override fun updateSurvey(id: Long, title: String?, description: String?,
                              canTakeAnonymously: Boolean?): Mono<Survey> = repository.findById(id).flatMap {
        repository.save(it.copy(id = it.id,
                                title = title ?: it.title,
                                description = description ?: it.description,
                                naturalKey = it.naturalKey))
    }

}
