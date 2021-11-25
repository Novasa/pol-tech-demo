package com.example.facts.injection

import com.example.database.DatabaseConfig
import com.example.database.DatabaseFactory
import com.example.facts.BuildConfig
import com.example.facts.database.Database
import com.example.facts.database.dao.FactsDao
import com.example.injection.DatabaseConsumerModule
import com.example.typeconverter.DateTimeTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DatabaseConsumerModule::class,
    ]
)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(factory: DatabaseFactory, dateTimeTypeConverter: DateTimeTypeConverter): Database {
        val passphrase = "test1234".toCharArray()
        val config = DatabaseConfig(Database::class.java, Database.NAME, passphrase, BuildConfig.DEBUG) {
            addTypeConverter(dateTimeTypeConverter)
        }

        return factory.createDatabase(config)
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class DaoModule {

        @Singleton
        @Provides
        fun provideFactsDao(database: Database): FactsDao = database.factsDao()
    }
}
