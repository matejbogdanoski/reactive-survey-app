package mk.ukim.finki.reactive_survey_app.repository

import mk.ukim.finki.reactive_survey_app.domain.QuestionAnswer
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionAnswerRepository : ReactiveCrudRepository<QuestionAnswer, Long>