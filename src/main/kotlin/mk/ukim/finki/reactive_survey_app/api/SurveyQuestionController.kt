package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionManagingService
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/surveys/{surveyId}/questions")
class SurveyQuestionController(
        private val service: SurveyQuestionService,
        private val managing: SurveyQuestionManagingService
) {

    @GetMapping
    fun findAllBySurvey(@PathVariable surveyId: Long) = service.findAllBySurveyId(surveyId)
            .map(SurveyStaticMapper::mapSurveyQuestionToResponseStatic)

    @PostMapping
    fun createSurveyQuestion(@PathVariable surveyId: Long) = service.createSurveyQuestion(surveyId)
            .map(SurveyStaticMapper::mapSurveyQuestionToResponseStatic)

    @PostMapping("/{surveyQuestionId}/duplicate")
    fun duplicateSurveyQuestion(@PathVariable surveyId: Long,
                                @PathVariable surveyQuestionId: Long): Mono<SurveyQuestionResponse> =
            managing.duplicateQuestion(surveyId, surveyQuestionId)
                    .map(SurveyStaticMapper::mapSurveyQuestionToResponseStatic)

    @PatchMapping("/{surveyQuestionId}")
    fun updateSurveyQuestionInfo(@PathVariable surveyQuestionId: Long,
                                 @RequestBody surveyQuestionUpdated: SurveyQuestionUpdateRequest) =
            service.editSurveyQuestionInfo(surveyQuestionId, surveyQuestionUpdated)
                    .map(SurveyStaticMapper::mapSurveyQuestionToResponseStatic)

    @PatchMapping("/{surveyQuestionId}/update-position/{newPosition}")
    fun updateSurveyQuestionPosition(@PathVariable surveyId: Long,
                                     @PathVariable surveyQuestionId: Long,
                                     @PathVariable newPosition: Int) =
            service.updateSurveyQuestionPosition(surveyQuestionId, newPosition)
                    .map(SurveyStaticMapper::mapSurveyQuestionToResponseStatic)

    @DeleteMapping("/{surveyQuestionId}")
    fun deleteSurveyQuestion(@PathVariable surveyQuestionId: Long) = service.deleteSurveyQuestion(surveyQuestionId)

}
