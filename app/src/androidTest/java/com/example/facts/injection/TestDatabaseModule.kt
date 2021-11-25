package com.example.facts.injection

import com.example.facts.database.dao.FactsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Singleton

@TestInstallIn(
    components = [
        SingletonComponent::class
    ],
    replaces = [
        DatabaseModule::class,
        DatabaseModule.DaoModule::class
    ]
)
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideMockFactsDao(): FactsDao = mockk {
        // Since the category flow might be called from the ui even if we aren't testing it, we need to mock a default behaviour
        every { categoryFlow() } returns emptyFlow()
    }
}
