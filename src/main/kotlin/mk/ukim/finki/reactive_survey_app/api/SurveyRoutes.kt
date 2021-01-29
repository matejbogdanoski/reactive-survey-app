package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyHandler
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val surveyRoutes = beans {
    bean {
        val surveyService = ref<SurveyService>()
        val handler = SurveyHandler(surveyService)
        coRouter {
            "/api/surveys".nest {
                GET("/{id}", handler::findById)
                GET("/my-surveys", handler::findAllSurveysByUserPageable)
                GET("/my-surveys/count", handler::countAllSurveysByUser)
                POST("/", handler::createSurvey)
                PATCH("/{surveyId}", handler::updateSurvey)
            }
        }
    }
}
