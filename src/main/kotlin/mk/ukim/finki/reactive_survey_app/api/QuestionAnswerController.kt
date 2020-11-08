package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/question-answers")
class QuestionAnswerController(
        private val service: QuestionAnswerService
) {

    @PostMapping
    fun addNewAnswersBulk(
            @RequestBody questionAnswerMap: Map<Long, Any?>): Flux<QuestionAnswer> = service.bulkCreateQuestionAnswers(
            questionAnswerMap)
}
