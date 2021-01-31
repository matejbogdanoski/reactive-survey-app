package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.AuthenticationHandler
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val authenticationRoutes = beans {
    bean {
        val service = ref<UserService>()
        val handler = AuthenticationHandler(service)
        coRouter {
            "/api/auth".nest {
                POST("/", handler::token)
                POST("/refresh", handler::refreshToken)
            }
        }
    }
}
