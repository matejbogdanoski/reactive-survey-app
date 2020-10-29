package mk.ukim.finki.reactive_survey_app.converters

import io.r2dbc.spi.Row
import mk.ukim.finki.reactive_survey_app.domain.Survey
import mk.ukim.finki.reactive_survey_app.domain.SurveyQuestion
import mk.ukim.finki.reactive_survey_app.utils.JsonUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue

@ReadingConverter
class SurveyReadingConverter : Converter<Row, Survey> {

    override fun convert(row: Row): Survey? {
        val questionsJson = row.get("questions", String::class.java)
        val questions: List<SurveyQuestion>? = JsonUtils.fromJsonArray(questionsJson)
        return Survey(
                id = row.get("id") as Long,
                title = row.get("title", String::class.java),
                description = row.get("description", String::class.java),
                naturalKey = row.get("natural_key", String::class.java) ?: "",
                canTakeAnonymously = row.get("can_take_anonymously") as Boolean,
                questions = questions ?: emptyList()
        )
    }

}

@WritingConverter
class SurveyWritingConverter : Converter<Survey, OutboundRow> {

    override fun convert(source: Survey): OutboundRow? {
        val row = OutboundRow()
        with(source) {
            row.put("title", SettableValue.fromOrEmpty(title, String::class.java))
            row.put("description", SettableValue.fromOrEmpty(description, String::class.java))
            row.put("natural_key", SettableValue.from(naturalKey))
            row.put("can_take_anonymously", SettableValue.from(canTakeAnonymously))
        }
        return row
    }

}
