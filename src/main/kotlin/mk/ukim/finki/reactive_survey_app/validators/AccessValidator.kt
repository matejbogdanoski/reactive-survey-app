package mk.ukim.finki.reactive_survey_app.validators

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.security.access.AccessDeniedException
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.util.function.Tuple2

object AccessValidator {

    fun validateCanViewSurvey(surveyMono: Mono<Survey>, userMono: Mono<User>) =
            surveyMono.zipWith(userMono)
                    .handle { it, sink: SynchronousSink<Tuple2<Survey, User>> ->
                        if (it.t2.id != it.t1.createdBy) {
                            sink.error(AccessDeniedException("You do not have access to this survey!"))
                        } else {
                            sink.next(it)
                        }
                    }

    fun validateCanViewSurveyInstance(surveyInstanceMono: Mono<SurveyInstance>, userMono: Mono<User>) =
            surveyInstanceMono.zipWith(userMono)
                    .handle { it, sink: SynchronousSink<Tuple2<SurveyInstance, User>> ->
                        if (it.t1.takenBy != it.t2.id) {
                            sink.error(AccessDeniedException("You do not have access to this survey instance!"))
                        } else {
                            sink.next(it)
                        }
                    }
}
