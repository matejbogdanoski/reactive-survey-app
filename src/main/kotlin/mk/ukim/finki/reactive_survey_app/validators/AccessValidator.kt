package mk.ukim.finki.reactive_survey_app.validators

import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyInstance
import org.springframework.security.access.AccessDeniedException
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink

object AccessValidator {

    fun validateCanViewSurvey(surveyMono: Mono<Survey>, initiatedBy: Long) =
            surveyMono.handle { it, sink: SynchronousSink<Survey> ->
                if (it.id != initiatedBy) {
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

    fun validateCanEditUserInfo(initiatedBy: Long, userId: Long): Mono<Void> =
            if (initiatedBy != userId) Mono.error(
                    AccessDeniedException("You cannot edit others user info!")) else Mono.empty()


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
}
