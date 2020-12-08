package mk.ukim.finki.reactive_survey_app.service.impl.managing

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SurveyQuestionManagingServiceImpl(
        private val questionService: SurveyQuestionService,
        private val optionService: SurveyQuestionOptionService
) : SurveyQuestionManagingService {

    override fun duplicateQuestion(surveyId: Long, surveyQuestionId: Long): Mono<SurveyQuestion> =
            questionService.duplicateSurveyQuestion(surveyId, surveyQuestionId)
                    .flatMapMany { optionService.duplicateAllBySurveyQuestionId(surveyQuestionId, it.id!!) }
                    .last()
                    .flatMap { questionService.findById(it.surveyQuestionId) }

}
