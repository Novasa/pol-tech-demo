package com.example.network.service

import kotlinx.serialization.json.JsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ServiceConfig<TService : Any>(
    val baseUrl: String,
    val serviceClass: Class<TService>
) {

    companion object {
        inline fun <reified TService : Any> withBaseUrl(
            baseUrl: String,
            noinline setup: (ServiceConfig<TService>.() -> Unit)? = null

        ): ServiceConfig<TService> = ServiceConfig(baseUrl, TService::class.java).also {
            setup?.invoke(it)
        }
    }

    var client: (OkHttpClient.Builder.() -> Unit)? = null
    var json: (JsonBuilder.() -> Unit)? = null
    var retrofit: (Retrofit.Builder.() -> Unit)? = null

    var contentType: String = "application/json"
    var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
}
