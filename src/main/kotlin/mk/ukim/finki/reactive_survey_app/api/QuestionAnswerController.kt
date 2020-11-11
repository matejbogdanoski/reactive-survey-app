package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import mk.ukim.finki.reactive_survey_app.service.QuestionAnswerService
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/question-answers")
class QuestionAnswerController(
        private val service: QuestionAnswerService
) {

    @PostMapping("{surveyId}")
    fun addNewAnswersBulk(@RequestBody questionAnswerMap: Map<Long, Any?>,
                          @PathVariable surveyId: Long): Flux<QuestionAnswer> = service.bulkCreateQuestionAnswers(
            questionAnswerMap, surveyId)

    @GetMapping("/stream/{surveyId}", produces = [TEXT_EVENT_STREAM_VALUE])
    fun getAnswerStream(@PathVariable surveyId: Long) = service.getAnswerStream(surveyId)
}
