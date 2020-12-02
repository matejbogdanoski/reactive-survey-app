package mk.ukim.finki.reactive_survey_app.responses

import mk.ukim.finki.reactive_survey_app.responses.renderer.SurveyRendererResponse

data class SurveyInstancePreview(
        val survey: SurveyRendererResponse,
        val surveyInstance: SurveyInstanceResponse
)
