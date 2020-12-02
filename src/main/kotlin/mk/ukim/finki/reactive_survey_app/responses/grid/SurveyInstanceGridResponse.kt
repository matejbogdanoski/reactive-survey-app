package mk.ukim.finki.reactive_survey_app.responses.grid

import java.time.ZonedDateTime

data class SurveyInstanceGridResponse(
        val id: Long,
        val surveyTitle: String,
        val dateTaken: ZonedDateTime
)
