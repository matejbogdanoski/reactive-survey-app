package mk.ukim.finki.reactive_survey_app

import mk.ukim.finki.reactive_survey_app.api.apiRoutes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveSurveyAppApplication

fun main(args: Array<String>) {
    runApplication<ReactiveSurveyAppApplication>(*args) {
        addInitializers(
                *apiRoutes
        )
    }
}
