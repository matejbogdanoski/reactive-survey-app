package mk.ukim.finki.reactive_survey_app.domain

import mk.ukim.finki.reactive_survey_app.constants.Schemas
import mk.ukim.finki.reactive_survey_app.domain.User.Companion.TABLE_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("${Schemas.ACCESS}.${TABLE_NAME}")
data class User(
        @Id
        val id: Long?,

        val username: String,

        val email: String,

        val firstName: String,

        val lastName: String,

        val passwordHash: String
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}
