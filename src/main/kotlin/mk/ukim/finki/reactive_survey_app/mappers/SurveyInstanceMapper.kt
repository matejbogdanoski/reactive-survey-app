package mk.ukim.finki.reactive_survey_app.mappers

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.responses.QuestionAnswerResponse
import mk.ukim.finki.reactive_survey_app.responses.SurveyInstanceResponse
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SurveyInstanceMapper(
        private val questionAnswerService: QuestionAnswerService,
        private val questionService: SurveyQuestionService
) {

    fun mapSurveyInstanceToResponse(surveyInstance: SurveyInstance): Mono<SurveyInstanceResponse> = with(
            mapSurveyInstanceToResponseStatic(surveyInstance)) {
        questionAnswerService.findAllAnswersByInstanceId(id)
                .flatMap(::mapQuestionAnswerToResponse)
                .collectList()
                .map { copy(questionAnswers = it) }
    }


    fun mapQuestionAnswerToResponse(questionAnswer: QuestionAnswer): Mono<QuestionAnswerResponse> = with(
            mapQuestionAnswerToResponseStatic(questionAnswer)) {
        questionService.findById(questionAnswer.surveyQuestionId).map {
            copy(questionName = it.name ?: "")
        }
    }

    private fun mapSurveyInstanceToResponseStatic(surveyInstance: SurveyInstance) = with(surveyInstance) {
        SurveyInstanceResponse(id = id!!,
                               dateTaken = dateTaken.toString(),
                               questionAnswers = listOf())
    }

    private fun mapQuestionAnswerToResponseStatic(questionAnswer: QuestionAnswer) = with(questionAnswer) {
        QuestionAnswerResponse(questionName = "",
                               answer = answer)
    }

}
