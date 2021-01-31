package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.handlers.QuestionAnswerHandler
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

val questionAnswerRoutes = beans {
    bean {
        val service = ref<QuestionAnswerService>()
        val handler = QuestionAnswerHandler(service)
        coRouter {
            "/api/question-answers".nest {
                GET("/{questionId}", handler::findAllAnswersByQuestion)
            }
        }
    }
}
