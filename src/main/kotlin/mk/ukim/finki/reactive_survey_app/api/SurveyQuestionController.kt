package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyStaticMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyQuestionService
import org.springframework.web.bind.annotation.*

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

}
