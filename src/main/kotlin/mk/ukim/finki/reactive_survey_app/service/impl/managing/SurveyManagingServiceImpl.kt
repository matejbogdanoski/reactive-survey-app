package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.service.SurveyManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SurveyManagingServiceImpl(
        private val surveyService: SurveyService,
        private val surveyQuestionService: SurveyQuestionService,
        private val userService: UserService
) : SurveyManagingService {

    override fun createSurveyWithQuestion(createdBy: String): Mono<Survey> =
            userService.findByUsername(createdBy).flatMap { user ->
                surveyService.createSurvey(user.id!!)
                        .doOnNext { surveyQuestionService.createSurveyQuestion(it.id!!).subscribe() }
            }

    override fun createSurvey(createdBy: String): Mono<Survey> = userService.findByUsername(
            createdBy).flatMap { surveyService.createSurvey(it.id!!) }


    override fun findById(id: Long, initiatedBy: String): Mono<Survey> =
            userService.findByUsername(initiatedBy).let { surveyService.findById(id, it) }

    override fun findAllByUsernamePage(username: String,
                                       page: Int,
                                       size: Int): Flux<Survey> = userService.findByUsername(username)
            .flatMapMany { surveyService.findAllCreatedByPage(it.id!!, page, size) }

    override fun countAllByUsername(username: String): Mono<Int> = userService.findByUsername(
            username).flatMap { surveyService.countAllCreatedBy(it.id!!) }
}
