package mk.ukim.finki.reactive_survey_app.domain.enums

enum class QuestionType {
    SHORT_TEXT,
    LONG_TEXT,
    MULTIPLE_CHOICE,
    CHECKBOX,
    DROPDOWN,
    DATE,
    TIME;

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]
    }
}
