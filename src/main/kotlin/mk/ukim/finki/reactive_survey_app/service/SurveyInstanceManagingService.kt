package mk.ukim.finki.reactive_survey_app.service

import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.dto.AnswerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SurveyInstanceManagingService {
    fun createInstanceWithAnswers(questionAnswerMap: Map<Long, String?>, surveyId: Long,
                                  takenBy: String): Mono<SurveyInstance>

    fun streamInstanceAnswers(surveyId: Long): Flux<AnswerDTO?>
    fun findAllBySurveyId(surveyId: Long): Flux<SurveyInstance>
    fun findAllTakenByPage(username: String, size: Int, page: Int): Flux<SurveyInstance>
    fun countAllByUsername(username: String): Mono<Int>
}
