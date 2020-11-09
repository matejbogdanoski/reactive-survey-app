package mk.ukim.finki.reactive_survey_app.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonUtils {
    private val moshi by lazy {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    fun getMoshiReference(): Moshi = moshi

    inline fun <reified T> fromJsonArray(jsonString: String?): List<T>? =
            jsonString?.let {
                val parametrizedType = Types.newParameterizedType(List::class.java, T::class.java)
                getMoshiReference().adapter<List<T>>(parametrizedType).fromJson(it)
            }

    inline fun <reified T> fromJson(jsonString: String?): T? = jsonString?.let {
        getMoshiReference().adapter(T::class.java).fromJson(it)
    }
}
