package com.example.dogfacts.injection

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.model.DogImage
import com.example.dogfacts.repository.DogFactsRepository
import com.example.dogfacts.repository.DogImageDataSource
import com.example.network.repository.FlowDataSource
import com.example.network.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class)
@Module
interface DogFactsConsumerModule {

    @Binds
    fun bindDogFactsRepository(instance: DogFactsRepository): Repository<Int, List<DogFact>>

    @Binds
    fun bindDogImagesRepository(instance: DogImageDataSource): FlowDataSource<Int, DogImage>
}
