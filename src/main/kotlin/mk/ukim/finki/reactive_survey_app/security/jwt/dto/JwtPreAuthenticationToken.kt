package mk.ukim.finki.reactive_survey_app.security.jwt.dto

import org.springframework.security.authentication.AbstractAuthenticationToken
import javax.security.auth.Subject

class JwtPreAuthenticationToken(
        val authToken: String,
        val bearerRequestHeader: String,
        val username: String
) : AbstractAuthenticationToken(null) {

    init {
        isAuthenticated = false
    }

    override fun getCredentials(): Any? = null

    override fun getPrincipal(): Any? = null

    override fun implies(subject: Subject): Boolean = false

}
