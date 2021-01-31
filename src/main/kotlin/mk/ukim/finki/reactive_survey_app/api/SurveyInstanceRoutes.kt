package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyInstanceHandler
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInstanceMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceService
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

val surveyInstanceRoutes = beans {
    bean {
        val service = ref<SurveyInstanceService>()
        val managing = ref<SurveyInstanceManagingService>()
        val mapper = ref<SurveyInstanceMapper>()
        val handler = SurveyInstanceHandler(service, managing, mapper)
        coRouter {
            "/api/survey-instances".nest {
                    GET("/stream-answers/{surveyId}", handler::getAnswerStream)
                GET("/by-user", handler::findAllTakenSurveysPage)
                GET("/count-by-user", handler::countAllTakenBy)
                GET("/preview/{instanceId}", handler::findInstanceById)
                GET("/{surveyId}", handler::findAllBySurvey)
                POST("/{surveyId}", handler::createInstanceWithAnswers)
            }
        }
    }
}
