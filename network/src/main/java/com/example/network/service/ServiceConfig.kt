package com.example.network.service

import kotlinx.serialization.json.JsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ServiceConfig(
    val baseUrl: String,
    setup: (ServiceConfig.() -> Unit)? = null
) {

    var contentType: String = "application/json"

    var client: (OkHttpClient.Builder.() -> Unit)? = null
    var json: (JsonBuilder.() -> Unit)? = null
    var retrofit: (Retrofit.Builder.() -> Unit)? = null

    var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    init {
        setup?.invoke(this)
    }
}
