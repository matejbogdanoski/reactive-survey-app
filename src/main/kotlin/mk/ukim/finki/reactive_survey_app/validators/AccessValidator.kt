package mk.ukim.finki.reactive_survey_app.validators

import mk.ukim.finki.reactive_survey_app.domain.Survey
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
}
