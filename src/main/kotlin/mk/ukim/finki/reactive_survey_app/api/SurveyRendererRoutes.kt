package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyRendererHandler
import mk.ukim.finki.reactive_survey_app.mappers.SurveyRendererMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyRendererService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val surveyRendererRoutes = beans {
    bean {
        val service = ref<SurveyRendererService>()
        val mapper = ref<SurveyRendererMapper>()
        val handler = SurveyRendererHandler(service, mapper)
        coRouter {
            "/api/survey-renderer".nest {
                GET("/{naturalKey}", handler::findSurveyRendererByNaturalKey)
            }
        }
    }
}
