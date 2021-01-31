package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyQuestionHandler
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val surveyQuestionRoutes = beans {
    bean {
        val surveyQuestionService = ref<SurveyQuestionService>()
        val surveyQuestionManagingService = ref<SurveyQuestionManagingService>()
        val handler = SurveyQuestionHandler(surveyQuestionService, surveyQuestionManagingService)
        coRouter {
            "/api/surveys/{surveyId}/questions".nest {
                GET("/", handler::findAllBySurvey)
                POST("/", handler::createSurveyQuestion)
                POST("/{surveyQuestionId}/duplicate", handler::duplicateSurveyQuestion)
                PATCH("/{surveyQuestionId}", handler::updateSurveyQuestionInfo)
                PATCH("/{surveyQuestionId}/update-position/{newPosition}", handler::updateSurveyQuestionPosition)
                DELETE("/{surveyQuestionId}", handler::deleteSurveyQuestion)
            }
        }
    }
}
