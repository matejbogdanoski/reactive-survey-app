package mk.ukim.finki.reactive_survey_app.service.impl

import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.repository.SurveyQuestionOptionRepository
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SurveyQuestionOptionServiceImpl(
        private val repository: SurveyQuestionOptionRepository
) : SurveyQuestionOptionService {

    override fun findAllBySurveyQuestionId(
            surveyQuestionId: Long): Flux<SurveyQuestionOption> = repository.findAllBySurveyQuestionId(surveyQuestionId,
                                                                                                       Sort.by("position"))
}
