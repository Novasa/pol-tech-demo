package com.example.injection

import com.example.database.DatabaseFactory
import com.example.database.implementation.DatabaseFactoryImplementation
import dagger.Binds
import dagger.Module

@Module
interface DatabaseConsumerModule {

    @Binds
    fun bindDatabaseFactory(instance: DatabaseFactoryImplementation) : DatabaseFactory
}
