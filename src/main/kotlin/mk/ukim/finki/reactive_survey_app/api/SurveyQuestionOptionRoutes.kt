package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyQuestionOptionHandler
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val surveyQuestionOptionRoutes = beans {
    bean {
        val service = ref<SurveyQuestionOptionService>()
        val handler = SurveyQuestionOptionHandler(service)
        coRouter {
            "/api/surveys/questions/{questionId}/question-options".nest {
                GET("/", handler::findAllByQuestion)
                POST("/", handler::createQuestionOption)
                PATCH("/{questionOptionId}", handler::updateQuestionOptionLabel)
                PATCH("/{questionOptionId}/update-position/{newPosition}", handler::updateQuestionOptionPosition)
                DELETE("/{questionOptionId}", handler::deleteQuestionOption)
            }
        }
    }
}
