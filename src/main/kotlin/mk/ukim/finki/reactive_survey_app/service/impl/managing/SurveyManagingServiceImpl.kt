package mk.ukim.finki.reactive_survey_app.service.impl.managing

import kotlinx.coroutines.reactor.mono
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.service.SurveyManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyManagingServiceImpl(
        private val surveyService: SurveyService,
        private val surveyQuestionService: SurveyQuestionService
) : SurveyManagingService {

    override fun createSurveyWithQuestion(createdBy: Long): Mono<Survey> =
            //todo: change this later
            mono { surveyService.createSurvey(createdBy) }
                    .doOnNext { surveyQuestionService.createSurveyQuestion(it.id!!).subscribe() }

}
