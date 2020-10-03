package mk.ukim.finki.reactive_survey_app.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class MigrationsConfig(
        private val env: Environment
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway = Flyway(
            Flyway.configure()
                    .baselineOnMigrate(true)
                    .dataSource(
                            env.getRequiredProperty("spring.flyway.url"),
                            env.getRequiredProperty("spring.flyway.user"),
                            env.getRequiredProperty("spring.flyway.password")
                    )
    )

}
