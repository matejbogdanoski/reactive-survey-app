package mk.ukim.finki.reactive_survey_app.security

import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@ControllerAdvice
class SecurityControllerAdvice {

    @ModelAttribute
    fun csrfToken(exchange: ServerWebExchange): Mono<CsrfToken> = exchange.getAttribute<Mono<CsrfToken>>(
            CsrfToken::class.java.name)?.let {
        it.doOnSuccess { token ->
            exchange.attributes[CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME] = token
        }
    } ?: Mono.empty()

//    @ModelAttribute("currentUser")
//    fun currentUser(@AuthenticationPrincipal currentUser: Mono<User>): Mono<User> = currentUser

}
