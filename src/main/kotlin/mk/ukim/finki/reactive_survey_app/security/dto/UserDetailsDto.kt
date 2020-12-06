package mk.ukim.finki.reactive_survey_app.security.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDto(
        private val userName: String,
        private val passwordHash: String,
        val userId: Long
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            AuthorityUtils.createAuthorityList("ROLE_USER")

    override fun getPassword(): String = passwordHash

    override fun getUsername(): String = userName

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
