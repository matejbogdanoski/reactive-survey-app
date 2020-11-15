package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/question-answers")
class QuestionAnswerController(
        private val service: QuestionAnswerService
) {

    @GetMapping("/{questionId}")
    fun findAllAnswersByQuestion(@PathVariable questionId: Long) = service.findAllAnswersByQuestionId(questionId)
}
