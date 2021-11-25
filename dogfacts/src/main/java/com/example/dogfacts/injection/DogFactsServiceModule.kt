package com.example.dogfacts.injection

import com.example.network.injection.NetworkModule
import com.example.network.service.ServiceConfig
import com.example.network.service.ServiceFactory
import com.example.dogfacts.service.DogFactsService
import com.example.dogfacts.service.DogImageService
import com.example.network.serializer.DateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.modules.SerializersModule
import okhttp3.logging.HttpLoggingInterceptor
import java.time.ZonedDateTime
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class
    ]
)
class DogFactsServiceModule {

    @Singleton
    @Provides
    fun provideDogFactsService(serviceFactory: ServiceFactory) : DogFactsService {
        val config = ServiceConfig("https://dog-facts-api.herokuapp.com/api/v1/") {
            json = {
                ignoreUnknownKeys = false
            }
        }
        return serviceFactory.createService(DogFactsService::class.java, config)
    }

    @Singleton
    @Provides
    fun provideDogImageService(serviceFactory: ServiceFactory, dateTimeSerializer: DateTimeSerializer) : DogImageService {
        val config = ServiceConfig("https://dog.ceo/api/") {
            logLevel = HttpLoggingInterceptor.Level.BASIC

            json = {
                serializersModule = SerializersModule {
                    contextual(ZonedDateTime::class, dateTimeSerializer)
                }
            }
        }
        return serviceFactory.createService(DogImageService::class.java, config)
    }
}
