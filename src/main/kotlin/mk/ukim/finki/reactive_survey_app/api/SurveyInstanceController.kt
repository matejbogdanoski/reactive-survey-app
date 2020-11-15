package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import mk.ukim.finki.reactive_survey_app.service.SurveyInstanceManagingService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/survey-instances")
class SurveyInstanceController(
        private val service: SurveyInstanceManagingService
) {

    @GetMapping("/stream-answers/{surveyId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getAnswerStream(@PathVariable surveyId: Long): Flux<AnswerDTO?> = service.streamInstanceAnswers(surveyId)

    @PostMapping("{surveyId}")
    fun createInstanceWithAnswers(@RequestBody questionAnswerMap: Map<Long, Any?>,
                                  @PathVariable surveyId: Long): Mono<SurveyInstance> = service.createInstanceWithAnswers(
            questionAnswerMap, surveyId)


}
