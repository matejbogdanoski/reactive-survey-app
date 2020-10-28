package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestionOption
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionOptionToResponseStatic
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyQuestionToResponseStatic
import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper.mapSurveyToResponseStatic
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class SurveyMapper(
        private val surveyQuestionService: SurveyQuestionService,
        private val surveyQuestionOptionService: SurveyQuestionOptionService
) {

    fun mapSurveyToResponse(survey: Mono<Survey>): Mono<SurveyResponse> {
        val surveyQuestions: Flux<SurveyQuestionResponse> = survey
                .flatMapMany {
                    surveyQuestionService.findAllBySurveyId(it.id!!)
                }.flatMap {
                    Mono.just(it).let(::mapSurveyQuestionToResponse)
                }
        return survey.map(::mapSurveyToResponseStatic).flatMap { response ->
            surveyQuestions.collectList().map {
                response.copy(questions = it.apply { sortBy { q -> q.position } })
            }
        }
    }

    fun mapSurveyQuestionToResponse(surveyQuestion: Mono<SurveyQuestion>): Mono<SurveyQuestionResponse> {
        val questionOptions = surveyQuestion.flatMapMany {
            surveyQuestionOptionService.findAllBySurveyQuestionId(it.id!!)
        }.flatMap { Mono.just(it).let(::mapSurveyQuestionOptionToResponse) }

        return surveyQuestion.map(::mapSurveyQuestionToResponseStatic).flatMap { response ->
            questionOptions.collectList().map {
                response.copy(options = it.apply { sortBy { o -> o.position } })
            }
        }
    }

    fun mapSurveyQuestionOptionToResponse(
            surveyQuestionOptions: Mono<SurveyQuestionOption>): Mono<SurveyQuestionOptionResponse> = surveyQuestionOptions.map(
            ::mapSurveyQuestionOptionToResponseStatic)
}
