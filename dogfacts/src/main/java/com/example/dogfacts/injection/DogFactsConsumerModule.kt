package com.example.dogfacts.injection

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.model.DogImage
import com.example.dogfacts.repository.DogFactsRepository
import com.example.dogfacts.repository.DogImageRepository
import com.example.network.repository.FlowRepository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DogFactsServiceModule::class
    ]
)
interface DogFactsConsumerModule {

    @Binds
    fun bindDogFactsRepository(instance: DogFactsRepository) : FlowRepository<List<DogFact>>

    @Binds
    fun bindDogImagesRepository(instance: DogImageRepository) : FlowRepository<DogImage>
}
