package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.SurveyInvitationHandler
import mk.ukim.finki.reactive_survey_app.mappers.SurveyInvitationMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyInvitationService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val surveyInvitationRoutes = beans {
    bean {
        val managing = ref<SurveyInvitationManagingService>()
        val service = ref<SurveyInvitationService>()
        val mapper = ref<SurveyInvitationMapper>()
        val handler = SurveyInvitationHandler(managing, service, mapper)
        coRouter {
            "/api/survey-invitations".nest {
                GET("/pending", handler::findSurveyInvitationsPage)
                GET("/pending/count", handler::countSurveyInvitations)
                GET("/{surveyId}", handler::findInvitationsBySurvey)
                POST("/", handler::createSurveyInvitation)
            }
        }
    }
}
