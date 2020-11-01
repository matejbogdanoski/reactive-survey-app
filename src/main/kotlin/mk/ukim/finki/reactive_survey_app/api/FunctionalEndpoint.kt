package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyUpdateRequest
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class FunctionalEndpoint {

    @Bean
    fun surveyHttpRoutes(service: SurveyService) = router {
        "/api/surveys".nest {
            GET("/natural-key/{naturalKey}") {
                ServerResponse.ok().body(service.findOneByNaturalKey(it.pathVariable("naturalKey")))
            }
            GET("/{surveyId}") {
                ServerResponse.ok().body(service.findById(it.pathVariable("surveyId").toLong()))
            }
            POST("") {
                ServerResponse.ok().body(service.createSurvey())
            }
            PATCH("/{surveyId}") {
                val id = it.pathVariable("surveyId").toLong()
                val request = it.bodyToMono(SurveyUpdateRequest::class.java).flatMap { request ->
                    service.updateSurvey(id, request.title, request.description, request.canTakeAnonymously)
                }
                ServerResponse.ok().body(request)
            }
        }
    }

    @Bean
    fun surveyQuestionHttpRoutes(service: SurveyQuestionService) = router {
        "/api/surveys/{surveyId}/questions".nest {
            GET("") {
                val surveyId = it.pathVariable("surveyId").toLong()
                val findAllBySurveyId = service.findAllBySurveyId(surveyId).map { question ->
                    SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
                }
                ServerResponse.ok().body(findAllBySurveyId)
            }
            POST("") {
                val surveyId = it.pathVariable("surveyId").toLong()
                val publisher = service.createSurveyQuestion(surveyId).map { question ->
                    SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
                }
                ServerResponse.ok().body(publisher)
            }
        }
    }

}
