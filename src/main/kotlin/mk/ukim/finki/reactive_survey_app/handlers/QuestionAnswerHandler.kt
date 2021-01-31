package mk.ukim.finki.reactive_survey_app.handlers

import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait

class QuestionAnswerHandler(
        private val service: QuestionAnswerService
) {

    suspend fun findAllAnswersByQuestion(request: ServerRequest): ServerResponse {
        val questionId = request.pathVariable("questionId").toLong()
        val answers = service.findAllAnswersByQuestionId(questionId)
        return ok().bodyAndAwait(answers)
    }

}
