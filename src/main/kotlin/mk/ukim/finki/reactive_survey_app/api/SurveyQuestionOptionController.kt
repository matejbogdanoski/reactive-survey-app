package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionOptionLabelUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionOptionResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionOptionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys/questions/{questionId}/question-options")
class SurveyQuestionOptionController(
        private val service: SurveyQuestionOptionService
) {

    @GetMapping
    fun findAllByQuestion(
            @PathVariable questionId: Long): Flux<SurveyQuestionOptionResponse> = service.findAllBySurveyQuestionId(
            questionId).map(SurveyStaticMapper::mapSurveyQuestionOptionToResponseStatic)

    @PostMapping
    fun createQuestionOption(
            @PathVariable questionId: Long): Mono<SurveyQuestionOptionResponse> = service.createSurveyQuestionOption(
            questionId).map(SurveyStaticMapper::mapSurveyQuestionOptionToResponseStatic)

    @PatchMapping("/{questionOptionId}")
    fun updateQuestionOptionLabel(@PathVariable questionId: Long, @PathVariable questionOptionId: Long,
                                  @RequestBody request: SurveyQuestionOptionLabelUpdateRequest): Mono<SurveyQuestionOptionResponse> =
            service.updateQuestionOptionLabel(questionId, questionOptionId, request.changedLabel)
                    .map(SurveyStaticMapper::mapSurveyQuestionOptionToResponseStatic)

    @PatchMapping("/{questionOptionId}/update-position/{newPosition}")
    fun updateQuestionOptionPosition(@PathVariable questionId: Long,
                                     @PathVariable questionOptionId: Long,
                                     @PathVariable newPosition: Int): Mono<SurveyQuestionOptionResponse> =
            service.updateQuestionOptionPosition(questionId, questionOptionId, newPosition)
                    .map(SurveyStaticMapper::mapSurveyQuestionOptionToResponseStatic)

    @DeleteMapping("/{questionOptionId}")
    fun deleteQuestionOption(@PathVariable questionId: Long, @PathVariable questionOptionId: Long): Mono<Void> =
            service.deleteQuestionOption(questionId, questionOptionId)

}
