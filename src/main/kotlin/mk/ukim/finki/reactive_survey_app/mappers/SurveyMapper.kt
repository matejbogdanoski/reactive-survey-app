package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionOptionToResponseStatic
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionToResponseStatic
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyToResponseStatic
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SurveyMapper(
        private val surveyQuestionService: SurveyQuestionService,
        private val surveyQuestionOptionService: SurveyQuestionOptionService
) {

    fun mapSurveyToResponse(survey: Survey): Mono<SurveyResponse> = with(mapSurveyToResponseStatic(survey)) {
        surveyQuestionService.findAllBySurveyId(id)
                .flatMap(::mapSurveyQuestionToResponse)
                .collectList()
                .map { copy(questions = it) }
    }

    fun mapSurveyQuestionToResponse(surveyQuestion: SurveyQuestion): Mono<SurveyQuestionResponse> = with(
            mapSurveyQuestionToResponseStatic(surveyQuestion)) {
        surveyQuestionOptionService.findAllBySurveyQuestionId(id)
                .collectList()
                .map { copy(options = it.map(::mapSurveyQuestionOptionToResponseStatic)) }
    }

}
