package mk.ukim.finki.reactive_survey_app.validators

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import mk.ukim.finki.reactive_survey_app.domain.SurveyInvitation
import org.springframework.security.access.AccessDeniedException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.util.function.Tuple2

object AccessValidator {

    fun validateCanViewSurvey(surveyMono: Mono<Survey>, initiatedBy: Long) =
            surveyMono.handle { it, sink: SynchronousSink<Survey> ->
                if (it.createdBy != initiatedBy) {
                    sink.error(AccessDeniedException("You do not have access to this survey!"))
                } else {
                    sink.next(it)
                }
            }

    fun validateCanViewSurveyInstance(surveyInstanceMono: Mono<SurveyInstance>, userId: Long) =
            surveyInstanceMono.handle { it, sink: SynchronousSink<SurveyInstance> ->
                if (it.takenBy != userId) {
                    sink.error(AccessDeniedException("You do not have access to this survey instance!"))
                } else {
                    sink.next(it)
                }
            }

    fun validateCanCreateSurveyInvitation(initiatedBy: Long, surveyMono: Mono<Survey>): Mono<Survey> =
            surveyMono.handle { it, sink: SynchronousSink<Survey> ->
                if (initiatedBy != it.createdBy) {
                    sink.error(AccessDeniedException(
                            "You cannot send invitation for a survey that you didn't create!"))
                } else {
                    sink.next(it)
                }
            }

    fun validateCanViewSurveyInvitations(surveyMono: Mono<Survey>, initiatedBy: Long) =
            surveyMono.handle { it, sink: SynchronousSink<Survey> ->
                if (initiatedBy != it.createdBy) {
                    sink.error(
                            AccessDeniedException("You cannot view invitations for a survey that you didn't create!"))
                } else {
                    sink.next(it)
                }
            }

    fun validateCanViewSurveyStructure(surveyMono: Mono<Survey>,
                                       surveyInvitationsFlux: Flux<SurveyInvitation>, initiatedBy: Long) =
            surveyMono.zipWith(surveyInvitationsFlux.collectList())
                    .handle { tuple, sink: SynchronousSink<Tuple2<Survey, List<SurveyInvitation>>> ->
                        val (survey, surveyInvitations) = tuple.t1 to tuple.t2
                        if (survey.createdBy == initiatedBy) sink.next(tuple)
                        val userIds = surveyInvitations.filter { !it.taken }.map { it.userId }
                        if (userIds.contains(initiatedBy)) sink.next(tuple)
                        else sink.error(AccessDeniedException("You do not have a permission to access this survey!"))
                    }

    fun validateCanCreateSurveyInstance(surveyMono: Mono<Survey>,
                                        surveyInvitationsFlux: Flux<SurveyInvitation>, initiatedBy: Long) =
            surveyMono.zipWith(surveyInvitationsFlux.collectList())
                    .handle { tuple, sink: SynchronousSink<Tuple2<Survey, List<SurveyInvitation>>> ->
                        val (survey, surveyInvitations) = tuple.t1 to tuple.t2
                        if (survey.createdBy == initiatedBy)
                            sink.error(AccessDeniedException("You cannot submit answers to your own survey!"))
                        val userIds = surveyInvitations.filter { !it.taken }.map { it.userId }
                        if (userIds.contains(initiatedBy)) sink.next(tuple)
                        else sink.error(
                                AccessDeniedException("You do not have a permission to submit answers to this survey!"))
                    }
}
