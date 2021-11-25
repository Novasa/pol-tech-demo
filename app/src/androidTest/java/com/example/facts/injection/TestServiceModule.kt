package com.example.facts.injection

import android.net.Uri
import com.example.catfacts.injection.CatFactsServiceModule
import com.example.catfacts.model.CatFactsResponse
import com.example.catfacts.service.CatFactsService
import com.example.dogfacts.injection.DogFactsServiceModule
import com.example.dogfacts.model.DogImage
import com.example.dogfacts.service.DogFactsService
import com.example.dogfacts.service.DogImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.TestSingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import javax.inject.Singleton

@TestInstallIn(
    components = [
        SingletonComponent::class
    ],
    replaces = [
        DogFactsServiceModule::class,
        CatFactsServiceModule::class
    ]
)
@Module
class TestServiceModule {

    @Singleton
    @Provides
    fun provideMockDogFactsService(): DogFactsService = mockk {
        coEvery { getDogFacts(any()) } returns emptyList()
    }

    @Singleton
    @Provides
    fun provideMockDogImagesService(): DogImageService = mockk {
        coEvery { getDogImage() } returns DogImage(Uri.parse("test.url"), "test")
    }

    @Singleton
    @Provides
    fun provideMockCatFactsService(): CatFactsService = mockk {
        coEvery { getCatFacts(any(), any()) } returns CatFactsResponse(emptyList())
    }
}
