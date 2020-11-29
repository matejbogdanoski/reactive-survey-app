package mk.ukim.finki.reactive_survey_app.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClock
import mk.ukim.finki.reactive_survey_app.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtils(
        @Value("\${jwt.secret}")
        private val secret: String,
        @Value("\${jwt.expiration}")
        private val expiration: Long
) : Serializable {

    private val clock = DefaultClock.INSTANCE

    companion object {
        const val AUDIENCE_UNKNOWN = "unknown"
        const val AUDIENCE_MOBILE = "mobile"
        const val AUDIENCE_TABLET = "tablet"
    }

    fun getUsernameFromToken(token: String): String =
            getClaimFromToken<String>(token) { it.subject }


    fun getIssuedAtDateFromToken(token: String): Date =
            getClaimFromToken<Date>(token) { it.issuedAt }


    fun getExpirationDateFromToken(token: String): Date =
            getClaimFromToken<Date>(token) { it.expiration }


    fun getAudienceFromToken(token: String): String =
            getClaimFromToken<String>(token) { it.audience }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T =
            claimsResolver.apply(getAllClaimsFromToken(token))

    private fun getAllClaimsFromToken(token: String): Claims =
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body

    private fun isTokenExpired(token: String): Boolean = getExpirationDateFromToken(token).before(clock.now())

    private fun isCreatedBeforeLastPasswordReset(created: Date, lastPasswordReset: Date?): Boolean =
            lastPasswordReset != null && created.before(lastPasswordReset)

    private fun generateAudience(): String = AUDIENCE_UNKNOWN

    private fun ignoreTokenExpiration(token: String): Boolean {
        val audience = getAudienceFromToken(token)
        return AUDIENCE_TABLET == audience || AUDIENCE_MOBILE == audience
    }

    fun generateToken(user: User): String =
            doGenerateToken(HashMap<String, Any>(), user.username, generateAudience())

    private fun doGenerateToken(claims: Map<String, Any>, subject: String, audience: String): String {
        val createdDate = clock.now()
        val expirationDate = calculateExpirationDate(createdDate)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun canTokenBeRefreshed(token: String, lastPasswordReset: Date?): Boolean =
            (!isCreatedBeforeLastPasswordReset(getIssuedAtDateFromToken(token), lastPasswordReset)
                    && (!isTokenExpired(token) || ignoreTokenExpiration(token)))

    fun refreshToken(token: String): String? {
        val createdDate = clock.now()
        val expirationDate = calculateExpirationDate(createdDate)
        val claims = getAllClaimsFromToken(token)
        claims.issuedAt = createdDate
        claims.expiration = expirationDate
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun validateToken(token: String): Boolean = !isTokenExpired(token)

    private fun calculateExpirationDate(createdDate: Date): Date = Date(createdDate.time + expiration * 1000)

}
