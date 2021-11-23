package com.example.dogfacts.injection

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.model.DogImage
import com.example.dogfacts.repository.DogFactsRepository
import com.example.dogfacts.repository.DogImageDataSource
import com.example.network.repository.FlowDataSource
import com.example.network.repository.Repository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DogFactsServiceModule::class
    ]
)
interface DogFactsConsumerModule {

    @Binds
    fun bindDogFactsRepository(instance: DogFactsRepository) : Repository<Int, List<DogFact>>

    @Binds
    fun bindDogImagesRepository(instance: DogImageDataSource) : FlowDataSource<Int, DogImage>
}
