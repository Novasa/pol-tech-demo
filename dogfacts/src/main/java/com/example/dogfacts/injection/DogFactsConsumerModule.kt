package com.example.dogfacts.injection

import com.example.dogfacts.model.DogImage
import com.example.dogfacts.repository.DogFactsRepository
import com.example.dogfacts.repository.DogFactsRepositoryImplementation
import com.example.dogfacts.repository.DogImageChannel
import com.example.utility.coroutines.FlowChannel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface DogFactsConsumerModule {

    @Binds
    fun bindDogFactsRepository(instance: DogFactsRepositoryImplementation): DogFactsRepository

    @Binds
    fun bindDogImagesRepository(instance: DogImageChannel): FlowChannel<Int, DogImage>
}
