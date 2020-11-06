package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.requests.SurveyQuestionUpdateRequest
import mk.ukim.finki.reactive_survey_app.responses.SurveyQuestionResponse
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/surveys/{surveyId}/questions")
class SurveyQuestionController(
        private val service: SurveyQuestionService
) {

    @GetMapping
    fun findAllBySurvey(@PathVariable surveyId: Long) = service.findAllBySurveyId(surveyId).map { question ->
        SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
    }

    @PostMapping
    fun createSurveyQuestion(@PathVariable surveyId: Long) = service.createSurveyQuestion(surveyId).map { question ->
        SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
    }

    @PostMapping("/{surveyQuestionId}/duplicate")
    fun duplicateSurveyQuestion(@PathVariable surveyId: Long,
                                @PathVariable surveyQuestionId: Long): Flux<SurveyQuestionResponse> =
            service.duplicateSurveyQuestion(surveyId, surveyQuestionId)
                    .map { question -> SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question) }

    @DeleteMapping("/{surveyQuestionId}")
    fun deleteSurveyQuestion(@PathVariable surveyQuestionId: Long) = service.deleteSurveyQuestion(surveyQuestionId)

    @PatchMapping("/{surveyQuestionId}")
    fun updateSurveyQuestionInfo(@PathVariable surveyQuestionId: Long,
                                 @RequestBody surveyQuestionUpdated: SurveyQuestionUpdateRequest) = service.editSurveyQuestionInfo(
            surveyQuestionId, surveyQuestionUpdated).map { question ->
        SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
    }

    @PatchMapping("/{surveyQuestionId}/update-position/{newPosition}")
    fun updateSurveyQuestionPosition(@PathVariable surveyId: Long,
                                     @PathVariable surveyQuestionId: Long,
                                     @PathVariable newPosition: Int) =
            service.updateSurveyQuestionPosition(surveyQuestionId, newPosition).map { question ->
                SurveyStaticMapper.mapSurveyQuestionToResponseStatic(question)
            }

}
