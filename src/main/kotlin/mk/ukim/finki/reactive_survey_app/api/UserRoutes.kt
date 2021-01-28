package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.UserHandler
import mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils
import mk.ukim.finki.reactive_survey_app.service.UserService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter


val userRoutes = beans {
    bean {
        val userService = ref<UserService>()
        val tokenUtils = ref<JwtTokenUtils>()
        val handler = UserHandler(userService, tokenUtils)
        coRouter {
            "api/users".nest {
                GET("/user-info", handler::findUserInfo)
                POST("/signup", handler::signup)
                PATCH("/user-info/{id}", handler::editUserInfo)
                PATCH("/update-password", handler::updatePassword)
            }
        }
    }
}
