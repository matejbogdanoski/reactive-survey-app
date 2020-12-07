package mk.ukim.finki.reactive_survey_app.api

import mk.ukim.finki.reactive_survey_app.mappers.SurveyRendererMapper
import mk.ukim.finki.reactive_survey_app.security.jwt.dto.JwtAuthenticationToken
import mk.ukim.finki.reactive_survey_app.service.SurveyRendererService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/survey-renderer")
class SurveyRendererController(
        private val service: SurveyRendererService,
        private val mapper: SurveyRendererMapper
) {

    @GetMapping("/{naturalKey}")
    fun findSurveyRendererByNaturalKey(@AuthenticationPrincipal principal: JwtAuthenticationToken,
                                       @PathVariable naturalKey: String) =
            service.findSurveyStructure(naturalKey, principal.userId).flatMap(mapper::mapSurveyToResponse)
}
