package com.example.animalfacts.injection

import com.example.catfacts.injection.CatFactsConsumerModule
import com.example.dogfacts.injection.DogFactsConsumerModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module(
    includes = [
        DogFactsConsumerModule::class,
        CatFactsConsumerModule::class
    ]
)
interface AnimalFactsConsumerModule