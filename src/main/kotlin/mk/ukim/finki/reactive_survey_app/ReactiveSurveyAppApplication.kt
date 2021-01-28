package mk.ukim.finki.reactive_survey_app

import mk.ukim.finki.reactive_survey_app.api.userRoutes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration

@SpringBootApplication
class ReactiveSurveyAppApplication

fun main(args: Array<String>) {
    BlockHound.install(BlockHoundIntegration {
        it.allowBlockingCallsInside("kotlin.reflect.jvm.ReflectJvmMapping", "getKotlinFunction")
        it.allowBlockingCallsInside("java.base/java.util.Properties", "load")
        //encoding password is a blocking operation and should always be published on parallel worker
        it.allowBlockingCallsInside("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder", "encode")
        it.allowBlockingCallsInside("mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils", "doGenerateToken")
        it.allowBlockingCallsInside("mk.ukim.finki.reactive_survey_app.security.jwt.JwtTokenUtils",
                                    "getAllClaimsFromToken")
        it.allowBlockingCallsInside("com.fasterxml.jackson.module.kotlin.KotlinNamesAnnotationIntrospector",
                                    "hasCreatorAnnotation")
        it.allowBlockingCallsInside("kotlinx.coroutines.reactive.ReactiveFlowKt", "<clinit>")
    })
    runApplication<ReactiveSurveyAppApplication>(*args) {
        addInitializers(
                userRoutes
        )
    }
}
