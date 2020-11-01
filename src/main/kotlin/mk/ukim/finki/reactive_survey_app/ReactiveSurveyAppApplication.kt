package mk.ukim.finki.reactive_survey_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@SpringBootApplication
class ReactiveSurveyAppApplication

fun main(args: Array<String>) {
    BlockHound.builder()
//            .allowBlockingCallsInside("java.io.RandomAccessFile", "readBytes")
            .install()

    runApplication<ReactiveSurveyAppApplication>(*args)
}
