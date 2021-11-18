package com.example.catfacts.injection

import com.example.network.injection.NetworkModule
import com.example.network.service.ServiceConfig
import com.example.network.service.ServiceFactory
import com.example.catfacts.service.CatFactsService
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class
    ]
)
class CatFactsServiceModule {

    @Provides
    fun provideCatFactsService(serviceFactory: ServiceFactory) : CatFactsService {
        val config = ServiceConfig("https://catfact.ninja/")
        return serviceFactory.createService(CatFactsService::class.java, config)
    }
}
