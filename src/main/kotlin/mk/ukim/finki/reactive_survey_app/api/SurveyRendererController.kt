package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyRendererMapper
import mk.ukim.finki.reactive_survey_app.service.SurveyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/survey-renderer")
class SurveyRendererController(
        private val service: SurveyService,
        private val mapper: SurveyRendererMapper
) {

    @GetMapping("/{naturalKey}")
    fun findSurveyRendererByNaturalKey(@PathVariable naturalKey: String) = service.findOneByNaturalKey(naturalKey)
            .flatMap(mapper::mapSurveyToResponse)
}
