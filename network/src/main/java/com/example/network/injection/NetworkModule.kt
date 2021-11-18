package com.example.network.injection

import com.example.network.implementation.ServiceFactoryImplementation
import com.example.network.service.ServiceFactory
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Binds
    fun bindServiceFactory(instance: ServiceFactoryImplementation): ServiceFactory
}
