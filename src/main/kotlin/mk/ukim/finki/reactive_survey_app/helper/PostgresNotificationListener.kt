package mk.ukim.finki.reactive_survey_app.helper

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.api.Notification
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class PostgresNotificationListener(
        @Qualifier("connectionFactory")
        private val connectionFactory: ConnectionFactory
) {
    private final val connection: PostgresqlConnectionFactory =
            (connectionFactory as ConnectionPool).unwrap() as PostgresqlConnectionFactory

    fun listen(notification: String): Flow<Notification> = connection.create()
            .flatMapMany { it.createStatement("LISTEN $notification").execute().thenMany(it.notifications) }
            .share().asFlow()

}
