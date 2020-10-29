package mk.ukim.finki.reactive_survey_app.config

import io.r2dbc.spi.ConnectionFactory
import mk.ukim.finki.reactive_survey_app.converters.SurveyReadingConverter
import mk.ukim.finki.reactive_survey_app.converters.SurveyWritingConverter
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions

@Configuration
class R2dbcConfig : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
        TODO("Not yet implemented")
    }

    override fun r2dbcCustomConversions(): R2dbcCustomConversions {
        val converters = mutableListOf(
                SurveyReadingConverter(),
                SurveyWritingConverter()
        )
        return R2dbcCustomConversions(storeConversions, converters)
    }
}
