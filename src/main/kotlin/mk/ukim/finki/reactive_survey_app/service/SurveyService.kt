package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.enum.QuestionType
import mk.ukim.finki.reactive_survey_app.repository.SurveyRepository
import mk.ukim.finki.reactive_survey_app.responses.SelectOptionResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SurveyService(
        private val repository: SurveyRepository
) {

    fun findAllQuestionTypesAsSelectOptions(): Flux<SelectOptionResponse> = Flux.fromArray(QuestionType.values())
            .map {
                with(it) {
                    SelectOptionResponse(id = ordinal.toLong(), label = name)
                }
            }
}
