package com.example.network.implementation

import com.example.network.BuildConfig
import com.example.network.service.ServiceConfig
import com.example.network.service.ServiceFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalSerializationApi
class ServiceFactoryImplementation @Inject constructor() : ServiceFactory {

    override fun <TService : Any> createService(config: ServiceConfig<TService>): TService {

        val client = OkHttpClient.Builder().also { builder ->
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(HttpLoggingInterceptor().apply {
                    level = config.logLevel
                })
            }
        }.also { config.client?.invoke(it) }
            .build()

        val json = Json {
            ignoreUnknownKeys = true
            config.json?.invoke(this)
        }

        return Retrofit.Builder()
            .client(client)
            .baseUrl(config.baseUrl)
            .addConverterFactory(json.asConverterFactory(config.contentType.toMediaType()))
            .also { config.retrofit?.invoke(it) }
            .build()
            .create(config.serviceClass)
    }
}
