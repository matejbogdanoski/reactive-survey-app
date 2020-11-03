package mk.ukim.finki.reactive_survey_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration

@SpringBootApplication
class ReactiveSurveyAppApplication

fun main(args: Array<String>) {
    BlockHound.install(BlockHoundIntegration {
        it.allowBlockingCallsInside("kotlin.reflect.jvm.ReflectJvmMapping", "getKotlinFunction")
    })
    runApplication<ReactiveSurveyAppApplication>(*args)
}
