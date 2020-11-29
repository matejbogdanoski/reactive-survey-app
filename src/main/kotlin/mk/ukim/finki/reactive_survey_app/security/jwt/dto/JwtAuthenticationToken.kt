package mk.ukim.finki.reactive_survey_app.security.jwt.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
        token: String?,
        val username: String?,
        authorities: Collection<GrantedAuthority>
) : UsernamePasswordAuthenticationToken(token, username, authorities) {

    override fun getCredentials(): Any? = null
}
